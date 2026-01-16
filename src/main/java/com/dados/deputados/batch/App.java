package com.dados.deputados.batch;

import com.dados.deputados.batch.config.BatchProperties;
import com.dados.deputados.batch.downloader.FileDownloader;
import com.dados.deputados.batch.extractor.ZipExtractor;
import com.dados.deputados.batch.importer.CsvImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws Exception {

        logger.info("INICIO");

        // 1 - Download
        FileDownloader.downloadFiles(getUrls(), BatchProperties.get("csv.download.dir"));

        // 2 - Extração
        ZipExtractor.extractAll(BatchProperties.get("csv.download.dir"), BatchProperties.get("csv.extract.dir"));

        // 3 - Importação para MongoDB
        CsvImporter.importCsvFiles(BatchProperties.get("csv.extract.dir"));

        logger.info("FIM");
    }


    private static List<String> getUrls() {
        List<String> urls = new ArrayList<>();

        int fim = 2026;

        urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/deputados/csv/deputados.csv", 1, 1));
        urls.addAll(getUrls("https://www.camara.leg.br/cotas/Ano-%d.csv.zip", 2023, fim)); //2008
        urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/proposicoes/csv/proposicoes-%d.csv", 2023, fim)); //1934
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