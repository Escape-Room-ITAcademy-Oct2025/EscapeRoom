package com.escaperoom.app;

import com.escaperoom.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionTest {
    public static void main(String[] args) {
        System.out.println("🔌 Testing connection to MySQL...");

        try (Connection con = DatabaseConfig.getInstance().getConnection();
             Statement st = con.createStatement()) {

            System.out.println("✅ Connected to: " + con.getMetaData().getURL());
            System.out.println("📋 Tables found:");

            ResultSet rs = st.executeQuery("SHOW TABLES");
            while (rs.next()) {
                System.out.println(" - " + rs.getString(1));
            }

        } catch (Exception e) {
            System.err.println("❌ Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
