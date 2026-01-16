package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.config.DbConfig;
import com.dados.deputados.batch.dao.ProposicaoDAO;
import com.dados.deputados.batch.model.ImportFile;
import com.dados.deputados.batch.model.Proposicao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportProposicaoFileThread implements ImportFileThread {

    private final ImportFile anImportFile;

    private static final Logger logger = LogManager.getLogger(ImportProposicaoFileThread.class);

    public ImportProposicaoFileThread(ImportFile anImportFile) {
        this.anImportFile = anImportFile;
    }

    @Override
    public ImportFile call() throws SQLException {

        Connection c = DbConfig.getConnection(); c.setAutoCommit(false);

        ProposicaoDAO dao = new ProposicaoDAO();

        anImportFile.setInicio(LocalDateTime.now());

        Path target = anImportFile.getFile().toPath();

        int[] counters = new int[2]; // [0] imports, [1] errors

        try (BufferedReader br = Files.newBufferedReader(target)) {
            anImportFile.setFileSize(Files.size(target));

            StringBuilder sb = new StringBuilder();
            String line;
            boolean insideRecord = false;

            line = br.readLine();

            List<Proposicao> lista = new ArrayList<>();

            long lineNumber = 1; // header = 1
            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (!insideRecord) {
                    sb.setLength(0); // limpa buffer
                }
                sb.append(line).append("\n");

                // regra: se número de aspas é par, registro terminou
                long quotes = sb.chars().filter(ch -> ch == '"').count();
                if (quotes % 2 == 0) {
                    String registroCompleto = sb.toString();
                    try {
                        lista.add(Proposicao.fromArq(registroCompleto));
                        counters[0]++;

                        if (lista.size() >= bulkSize) {
                            dao.upsertBatch(c, lista);
                            c.commit();
                            lista.clear();
                        }
                    } catch (Exception e) {
                        counters[1]++;
                        logger.error("[Error : {}][{}][{}]", lineNumber, e.getMessage(), line, e);
                    }
                    insideRecord = false;
                } else {
                    insideRecord = true; // continua acumulando
                }
            }

            if (!lista.isEmpty()) {
                dao.upsertBatch(c, lista);
                c.commit();
            }
        } catch (IOException e) {
            counters[1]++;
            anImportFile.getResults().add("[Error: " + e.getMessage() + "][" + target + "]");
        }

        anImportFile.setTotalImport(counters[0]);
        anImportFile.setTotalErrors(counters[1]);
        anImportFile.setFim(LocalDateTime.now());

        logger.info("Import file: {} - {}", anImportFile.getFile().getName(), anImportFile.getTotalImport());

        return anImportFile;
    }
}