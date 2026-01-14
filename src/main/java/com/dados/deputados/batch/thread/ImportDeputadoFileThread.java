package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.config.DbConfig;
import com.dados.deputados.batch.dao.DeputadoDAO;
import com.dados.deputados.batch.model.Deputado;
import com.dados.deputados.batch.model.ImportFile;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
public class ImportDeputadoFileThread implements ImportFileThread {

    private final ImportFile anImportFile;

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
                                lista.clear();
                            }
                        } catch (Exception e) {
                            counters[1]++;
                            log.error("[Error : {}][{}][{}]", lineNumber, e.getMessage(), linha, e);
                        }
                    });

            if (!lista.isEmpty()) {
                dao.upsertBatch(c, lista);
            }
        } catch (IOException e) {
            counters[1]++;
            log.error("[Error : {}][{}]", lineNumber, e.getMessage(), e);
        }

        anImportFile.setTotalImport(counters[0]);
        anImportFile.setTotalErrors(counters[1]);
        anImportFile.setFim(LocalDateTime.now());

        return anImportFile;
    }
}