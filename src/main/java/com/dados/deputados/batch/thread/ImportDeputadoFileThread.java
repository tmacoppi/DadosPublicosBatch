package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.config.DbConfig;
import com.dados.deputados.batch.dao.DeputadoDAO;
import com.dados.deputados.batch.model.Deputado;
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
import java.util.stream.Stream;

public class ImportDeputadoFileThread implements ImportFileThread {

    private final ImportFile anImportFile;

    private static final Logger logger = LogManager.getLogger(ImportDeputadoFileThread.class);

    public ImportDeputadoFileThread(ImportFile anImportFile) {
        this.anImportFile = anImportFile;
    }

    @Override
    public ImportFile call() throws SQLException {
        Connection c = DbConfig.getConnection(); c.setAutoCommit(false);
        DeputadoDAO dao = new DeputadoDAO();

        anImportFile.setInicio(LocalDateTime.now());

        Path target = anImportFile.getFile().toPath();

        int[] counters = new int[2]; // [0] imports, [1] errors

        List<Deputado> lista = new ArrayList<>();

        AtomicLong lineNumber = new AtomicLong(1); // header = 1
        try (Stream<String> linhas = Files.lines(target)) {
            anImportFile.setFileSize(Files.size(target));

            linhas.skip(1) // pula cabeçalho
                    //.parallel() // ativa paralelismo
                    .forEach(linha -> {
                        try {
                            lineNumber.getAndIncrement();
                            lista.add(Deputado.fromArq(linha));

                            counters[0]++; // totalImport

                            if (lista.size() >= bulkSize) {
                                dao.upsertBatch(c, lista);
                                c.commit();
                                lista.clear();
                            }
                        } catch (Exception e) {
                            counters[1]++; //totalErrors
                            logger.error("[Error : {}][{}][{}]", lineNumber, e.getMessage(), linha, e);
                        }
                    });

            if (!lista.isEmpty()) {
                dao.upsertBatch(c, lista);
                c.commit();
            }

            c.close();
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