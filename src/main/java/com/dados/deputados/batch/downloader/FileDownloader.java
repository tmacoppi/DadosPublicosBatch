package com.dados.deputados.batch.downloader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

public class FileDownloader {
    public static void downloadFiles(List<String> urls, String downloadDir) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            for (String url : urls) {
                HttpGet request = new HttpGet(url);
                try (CloseableHttpResponse response = client.execute(request);
                     InputStream in = response.getEntity().getContent()) {

                    String fileName = Paths.get(url).getFileName().toString();
                    FileOutputStream out = new FileOutputStream(downloadDir + "/" + fileName);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    out.close();
                }
            }
        }
    }
}

