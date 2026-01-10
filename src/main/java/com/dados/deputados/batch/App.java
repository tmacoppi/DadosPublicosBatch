package com.dados.deputados.batch;

import com.dados.deputados.batch.downloader.FileDownloader;
import com.dados.deputados.batch.extractor.ZipExtractor;
import com.dados.deputados.batch.importer.CsvImporter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        // Carregar application.properties
        Properties props = new Properties();
        try (InputStream input = App.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Não foi possível encontrar application.properties");
            }
            props.load(input);
        }

        // Buscar valores configurados
        String downloadDir = props.getProperty("csv.download.dir");
        String extractDir = props.getProperty("csv.extract.dir");
        String mongoUri = props.getProperty("mongodb.uri");
        String mongoDb = props.getProperty("mongodb.database");
        String mongoCollection = props.getProperty("mongodb.collection");


        // 1 - Download
        FileDownloader.downloadFiles(getUrls(), downloadDir);

        // 2 - Extração
        ZipExtractor.extractAll(downloadDir, extractDir);

        // 3 - Importação para MongoDB
        //CsvImporter.importCsvFiles(extractDir, "minhaBase", "minhaColecao");
    }


    private static List<String> getUrls() {
        List<String> urls = new ArrayList<>();

        int fim = 2026;

        urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/deputados/csv/deputados.csv", 1, 1));
        urls.addAll(getUrls("https://www.camara.leg.br/cotas/Ano-%d.csv.zip", 2023, fim)); //2008
        //urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/proposicoes/csv/proposicoes-%d.csv", 1934, fim)); //1934
        //urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/legislaturas/csv/legislaturas.csv", 1, 1)); //1934

        return urls;
    }

    private static List<String> getUrls(String url, int ini, int fim){
        List<String> urls = new ArrayList<>();

        for (int i = ini; i <= fim; i++) {
            urls.add(String.format(url, i));
        }

        return urls;
    }
}