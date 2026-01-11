package com.dados.deputados.batch.importer;

import com.dados.deputados.batch.model.ImportExecution;
import com.dados.deputados.batch.model.ImportFile;
import com.dados.deputados.batch.thread.ImportFileThreadFactory;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

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

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CsvImporter {
    public static void importCsvFiles(String extractDir, String mongoDbHost, String mongoDbPort, String mongoDb) throws Exception {
        ExecutorService executorImport = Executors.newFixedThreadPool(5);
        ImportExecution ie = ImportExecution.builder()
                .startTime(LocalDateTime.now())
                .importedFiles(new ArrayList<>())
                .importedWebServices(new ArrayList<>())
                .build();

        //try (MongoClient mongoClient = MongoClients.create("mongodb://" + mongoDbHost + ":" + mongoDbPort)) {
        MongoDatabase database = createDatabase(mongoDbHost, mongoDbPort, mongoDb); //mongoClient.getDatabase(mongoDb);
        List<Future<ImportFile>> futuresImport = new ArrayList<>();
        ImportFileThreadFactory importFileThreadFactory = new ImportFileThreadFactory();

        //MongoCollection<Document> collection = database.getCollection(mongoDbCollection);

        File folder = new File(extractDir);
        for (File file : Objects.requireNonNull(folder.listFiles())) {

            futuresImport.add(
                    executorImport.submit(
                            importFileThreadFactory.createTask(ImportFile.builder()
                                            .extractDir(extractDir)
                                            .fileName(file.getName())
                                    .build(),
                                    database)));

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

        MongoCollection<ImportExecution> collection = database.getCollection("import_execution", ImportExecution.class);

        collection.insertOne(ie);

        executorImport.shutdown();
    }

    public static MongoDatabase createDatabase(String mongoDbHost, String mongoDbPort, String mongoDb) {
        // URI de conexão (com usuário e senha, se necessário)
        String uri = "mongodb://" + mongoDbHost + ":" + mongoDbPort + "/" + mongoDb; //"mongodb://localhost:27017/minhaBase";

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(uri))
                .codecRegistry(pojoCodecRegistry)
                .build();

        MongoClient client = MongoClients.create(settings);

        return client.getDatabase(mongoDb);
    }
}

