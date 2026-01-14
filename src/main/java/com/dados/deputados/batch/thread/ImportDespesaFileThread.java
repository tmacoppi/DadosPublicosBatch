package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.config.DbConfig;
import com.dados.deputados.batch.dao.DespesaDAO;
import com.dados.deputados.batch.model.Despesa;
import com.dados.deputados.batch.model.ImportFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class ImportDespesaFileThread implements ImportFileThread {

    private final ImportFile anImportFile;

    private static final Logger logger = LogManager.getLogger(ImportDespesaFileThread.class);

    public ImportDespesaFileThread(ImportFile anImportFile) {
        this.anImportFile = anImportFile;
    }

    @Override
    public ImportFile call() throws SQLException {

        Connection c = DbConfig.getConnection(); c.setAutoCommit(false);

        DespesaDAO dao = new DespesaDAO();

        anImportFile.setInicio(LocalDateTime.now());

        Path target = anImportFile.getFile().toPath();

        int[] counters = new int[2]; // [0] imports, [1] errors

        List<Despesa> lista = new ArrayList<>();

        AtomicLong lineNumber = new AtomicLong(1); // header = 1
        try (Stream<String> linhas = Files.lines(target)) {
            anImportFile.setFileSize(Files.size(target));

            AtomicReference<String> linhaImport = new AtomicReference<>("");

            linhas.skip(1) // pula cabeçalho
                    //.parallel() // ativa paralelismo
                    .forEach(linha -> {
                        try {
                            if (!linha.endsWith("\"") || !linha.startsWith("\"")){
                                logger.error("[Linha imcompleta : [{}][{}][{}]", lineNumber, anImportFile.getFile().getName(), linha);
                            } else {
                                lista.add(Despesa.fromArq(linha));
                                counters[0]++; // totalImport

                            }

                            if (lista.size() >= bulkSize) {
                                dao.upsertBatch(c, lista);
                                c.commit();
                                lista.clear();
                            }

                            lineNumber.addAndGet(1);
                        } catch (Exception e) {
                            counters[1]++; // totalErrors
                            logger.error("[Error : {}][{}][{}]", lineNumber, e.getMessage(), linha, e);
                        }
                    });

            if (!lista.isEmpty()) {
                dao.upsertBatch(c, lista);
                c.commit();
            }
        } catch (IOException e) {
            counters[1]++;
            logger.error("[Error : {}][{}]", lineNumber, e.getMessage(), e);
        }

        anImportFile.setTotalImport(counters[0]);
        anImportFile.setTotalErrors(counters[1]);
        anImportFile.setFim(LocalDateTime.now());

        logger.info("Import file: {} - {}", anImportFile.getFile().getName(), anImportFile.getTotalImport());

        return anImportFile;
    }
}