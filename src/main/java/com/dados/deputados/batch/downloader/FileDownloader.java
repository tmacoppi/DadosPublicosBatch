package com.dados.deputados.batch.downloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileDownloader {

    private static final Logger logger = LogManager.getLogger(FileDownloader.class);

    public static void downloadFiles(List<String> urls, String downloadDir) throws Exception {

        int retryMax = 3;
        Path target;
        String fileName;
        HttpRequest request;
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        for (String url : urls) {
            fileName = url.substring(url.lastIndexOf('/') + 1);
            target = Path.of(downloadDir.concat("/").concat(fileName));
            request = HttpRequest.newBuilder(URI.create(url)).GET().build();

            int retryCount = 0;

            logger.info("Download file: {}", url);

            do {

                try {
                    //Download
                    HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
                    logger.info("Download response [{}]", response.statusCode());

                    if (response.statusCode() != 200) {
                        retryCount++;
                        continue;
                    }
                    Files.write(target, response.body(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                    retryCount = retryMax;

                } catch (Exception e) {
                    //anImportFile.getResults().add("[Error: " + e.getMessage() + "]");
                    logger.error("[Error : download retries: {}][{}]", retryCount, e.getMessage(), e);
                    retryCount++;
                }

            } while (retryCount < retryMax);
        }
    }
}

