package com.dados.deputados.batch.thread;

import com.dados.deputados.batch.model.ImportFile;
import com.dados.deputados.batch.model.Legislatura;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Slf4j
public class ImportLegislaturaFileThread implements ImportFileThread {

    private final ImportFile anImportFile;

    public ImportLegislaturaFileThread(ImportFile anImportFile) {
        this.anImportFile = anImportFile;
    }

    @Override
    public ImportFile call() {

        /*
        anImportFile.setInicio(LocalDateTime.now());

        Path target = Path.of(anImportFile.getExtractDir(), anImportFile.getFileName());

        int[] counters = new int[2]; // [0] imports, [1] errors

        List<Legislatura> lista = new ArrayList<>();

        AtomicLong lineNumber = new AtomicLong(1); // header = 1
        try (Stream<String> linhas = Files.lines(target)) {
            anImportFile.setFileSize(Files.size(target));

            linhas.skip(1) // pula cabeçalho
                    .forEach(linha -> {
                        try {
                            lista.add(Legislatura.fromArq(linha));
                            counters[0]++; // totalImport

                            if (lista.size() >= bulkSize) {
                                collection.insertMany(lista);
                                lista.clear();
                            }
                        } catch (Exception e) {
                            anImportFile.getResults().add("[Error: " + e.getMessage() + "][" + linha + "]");
                            counters[1]++; // totalErrors
                        }
                    });

            if (!lista.isEmpty()) {
                collection.insertMany(lista);
            }
        } catch (IOException e) {
            counters[1]++;
            log.error("[Error : " + lineNumber + "][" + e.getMessage() + "]", e);
        }

        anImportFile.setTotalImport(counters[0]);
        anImportFile.setTotalErrors(counters[1]);
        anImportFile.setFim(LocalDateTime.now());


         */
        return anImportFile;
    }
}