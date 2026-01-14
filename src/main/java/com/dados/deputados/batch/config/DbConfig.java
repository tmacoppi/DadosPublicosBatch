package com.dados.deputados.batch.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new RuntimeException(e); }
    }
    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=%s&serverTimezone=UTC",
                BatchProperties.get("db.host"),
                BatchProperties.get("db.port"),
                BatchProperties.get("db.name"),
                BatchProperties.get("db.ssl"));
        return DriverManager.getConnection(url,
                BatchProperties.get("db.user"),
                BatchProperties.get("db.pass"));
    }
}

