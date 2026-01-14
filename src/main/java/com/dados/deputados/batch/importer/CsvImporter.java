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

        //MongoCollection<Document> collection = database.getCollection(mongoDbCollection);

        File folder = new File(extractDir);
        for (File file : Objects.requireNonNull(folder.listFiles())) {

            futuresImport.add(
                    executorImport.submit(
                            importFileThreadFactory.createTask(ImportFile.builder().file(file).build())));

            /*
            if (file.getName().endsWith(".csv")) {
                FileReader reader = new FileReader(file);
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

                for (CSVRecord record : records) {
                    Document doc = new Document();
                    record.toMap().forEach(doc::append);
                    collection.insertOne(doc);
                }
            }*/
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

        //MongoCollection<ImportExecution> collection = database.getCollection("import_execution", ImportExecution.class);

        //collection.insertOne(ie);

        executorImport.shutdown();
    }

}

