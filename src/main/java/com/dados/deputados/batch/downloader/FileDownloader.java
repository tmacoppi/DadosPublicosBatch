package com.dados.deputados.batch.downloader;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
public class FileDownloader {
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

            log.info("Download file: {}", url);

            do {

                try {
                    //Download
                    HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
                    log.info("Download response [{}]", response.statusCode());

                    if (response.statusCode() != 200) {
                        retryCount++;
                        continue;
                    }
                    Files.write(target, response.body(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                    retryCount = retryMax;

                } catch (Exception e) {
                    //anImportFile.getResults().add("[Error: " + e.getMessage() + "]");
                    log.error("[Error : download retries: " + retryCount + "][" + e.getMessage() + "]", e);
                    retryCount++;
                }

            } while (retryCount < retryMax);
        }
    }
}

