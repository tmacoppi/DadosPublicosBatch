package com.dados.deputados.batch.config;

import java.io.InputStream;
import java.util.Properties;

public class BatchProperties {
    private static final Properties props = new Properties();
    static {
        try (InputStream is = BatchProperties.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Erro carregando application.properties", e);
        }
    }
    public static String get(String key) { return props.getProperty(key); }
}

