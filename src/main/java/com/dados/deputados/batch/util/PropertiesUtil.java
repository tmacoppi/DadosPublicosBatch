package com.dados.deputados.batch.util;

import com.dados.deputados.batch.App;
import lombok.Data;

import java.io.InputStream;
import java.util.Properties;

@Data
public class PropertiesUtil {

    // Buscar valores configurados
    private String downloadDir;
    private String extractDir;
    private String mongoDbHost;
    private String mongoDbPort;
    private String mongoDb;

    public PropertiesUtil() {
        try {
            loadProperties();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() throws Exception {
        // Carregar application.properties
        Properties props = new Properties();
        try (
                InputStream input = App.class.getClassLoader()
                        .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Não foi possível encontrar application.properties");
            }
            props.load(input);
        }

        // Buscar valores configurados
        downloadDir = props.getProperty("csv.download.dir");
        extractDir = props.getProperty("csv.extract.dir");
        mongoDbHost = props.getProperty("mongodb.host");
        mongoDbPort = props.getProperty("mongodb.port");
        mongoDb = props.getProperty("mongodb.database");
    }



}
