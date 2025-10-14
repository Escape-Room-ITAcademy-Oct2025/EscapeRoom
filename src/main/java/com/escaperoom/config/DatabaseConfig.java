package com.escaperoom.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class to manage the database connection.
 */
public final class DatabaseConfig {
    private static volatile DatabaseConfig INSTANCE;
    private final String url;
    private final String user;
    private final String pass;

    private DatabaseConfig() {
        try (InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("db.properties")) {

            Properties props = new Properties();
            if (input == null) {
                throw new IllegalStateException("db.properties file not found in resources folder");
            }

            props.load(input);
            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.pass = props.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException("Error loading database configuration", e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseConfig();
                }
            }
        }
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
