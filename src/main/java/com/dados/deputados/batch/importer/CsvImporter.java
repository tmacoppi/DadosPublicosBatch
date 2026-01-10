package com.dados.deputados.batch.importer;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;

import java.io.File;
import java.io.FileReader;

public class CsvImporter {
    public static void importCsvFiles(String extractDir, String dbName, String collectionName) throws Exception {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            File folder = new File(extractDir);
            for (File file : folder.listFiles()) {
                if (file.getName().endsWith(".csv")) {
                    FileReader reader = new FileReader(file);
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

                    for (CSVRecord record : records) {
                        Document doc = new Document();
                        record.toMap().forEach(doc::append);
                        collection.insertOne(doc);
                    }
                }
            }
        }
    }
}

