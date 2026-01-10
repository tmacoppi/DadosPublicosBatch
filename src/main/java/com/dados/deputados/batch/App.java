package com.dados.deputados.batch;

import com.dados.deputados.batch.downloader.FileDownloader;
import com.dados.deputados.batch.extractor.ZipExtractor;
import com.dados.deputados.batch.importer.CsvImporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public static void main(String[] args) throws Exception {
        List<String> urls = Arrays.asList(
                "https://exemplo.com/arquivo1.csv",
                "https://exemplo.com/arquivo2.zip"
        );

        String downloadDir = "downloads";
        String extractDir = "extract";

        // 1 - Download
        FileDownloader.downloadFiles(urls, downloadDir);

        // 2 - Extração
        ZipExtractor.extractAll(downloadDir, extractDir);

        // 3 - Importação para MongoDB
        CsvImporter.importCsvFiles(extractDir, "minhaBase", "minhaColecao");
    }


    private List<String> getUrls() {
        List<String> urls = new ArrayList<>();

        int fim = 2025;

        //urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/deputados/csv/deputados.csv", 1, 1));
        //urls.addAll(getUrls("https://www.camara.leg.br/cotas/Ano-%d.csv.zip", 2008, fim)); //2008
        //urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/proposicoes/csv/proposicoes-%d.csv", 1934, fim)); //1934
        //urls.addAll(getUrls("https://dadosabertos.camara.leg.br/arquivos/legislaturas/csv/legislaturas.csv", 1, 1)); //1934

        return urls;
    }

    private List<String> getUrls(String url, int ini, int fim){
        List<String> urls = new ArrayList<>();

        for (int i = ini; i <= fim; i++) {
            urls.add(String.format(url, i));
        }

        return urls;
    }
}