package com.dados.deputados.batch;

import com.dados.deputados.batch.downloader.FileDownloader;
import com.dados.deputados.batch.extractor.ZipExtractor;
import com.dados.deputados.batch.importer.CsvImporter;
import com.dados.deputados.batch.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        PropertiesUtil prop = new PropertiesUtil();

        // 1 - Download
        FileDownloader.downloadFiles(getUrls(), prop.getDownloadDir());

        // 2 - Extração
        ZipExtractor.extractAll(prop.getDownloadDir(), prop.getExtractDir());

        // 3 - Importação para MongoDB
        CsvImporter.importCsvFiles(prop.getExtractDir(), prop.getMongoDbHost(), prop.getMongoDbPort(), prop.getMongoDb());
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