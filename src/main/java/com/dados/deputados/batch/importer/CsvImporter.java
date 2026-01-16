package com.dados.deputados.batch.importer;

import com.dados.deputados.batch.model.ImportExecution;
import com.dados.deputados.batch.model.ImportFile;
import com.dados.deputados.batch.thread.ImportFileThreadFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class CsvImporter {
    public static void importCsvFiles(String extractDir) throws Exception {
        ExecutorService executorImport = Executors.newFixedThreadPool(5);

        ImportExecution ie = ImportExecution.builder()
                .startTime(LocalDateTime.now())
                .importedFiles(new ArrayList<>())
                .importedWebServices(new ArrayList<>())
                .build();

        List<Future<ImportFile>> futuresImport = new ArrayList<>();
        ImportFileThreadFactory importFileThreadFactory = new ImportFileThreadFactory();

        File folder = new File(extractDir);
        for (File file : Objects.requireNonNull(folder.listFiles())) {

            futuresImport.add(
                    executorImport.submit(
                            importFileThreadFactory.createTask(ImportFile.builder().file(file).build())));

        }

        AtomicInteger totalFile = new AtomicInteger();

        futuresImport.forEach(f -> {
            try {
                totalFile.addAndGet(f.get().getTotalImport());
                ie.getImportedFiles().add(f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        ie.setEndTime(LocalDateTime.now());
        ie.setTotalFileLines(totalFile.get());

        executorImport.shutdown();
    }

}

