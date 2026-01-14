package com.dados.deputados.batch.extractor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    private static final Logger logger = LogManager.getLogger(ZipExtractor.class);

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
            logger.info("Extract file: {}", file.getName());
            while ((entry = zis.getNextEntry()) != null) {
                try (OutputStream os = Files.newOutputStream(Path.of(dest.concat("/").concat(entry.getName())))) {
                    zis.transferTo(os);
                    logger.info("\t{}", entry.getName());
                }

                zis.closeEntry();
            }
        }
    }

    private static void move(File file, String dest) {
        try {
            logger.info("Move file: {}", file.getName());
            Files.move(file.toPath(), Path.of(dest.concat("/").concat(file.getName())));
        } catch (IOException e) {

        }
    }
}

