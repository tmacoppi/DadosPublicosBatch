package com.dados.deputados.batch.extractor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {
    public static void extractAll(String downloadDir, String extractDir) throws Exception {
        File folder = new File(downloadDir);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".zip")) {
                unzip(file, extractDir);
            } else {
                move(file, extractDir);
            }
        }
    }

    private static void unzip(File file, String dest) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(file.toPath()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                try (OutputStream os = Files.newOutputStream(Path.of(dest.concat("/").concat(entry.getName())))) {
                    zis.transferTo(os);
                }

                zis.closeEntry();
            }
        }
    }

    private static void move(File file, String dest) {
        try {
            Files.move(file.toPath(), Path.of(dest.concat("/").concat(file.getName())));
        } catch (IOException e) {

        }
    }
}

