package com.Inventory.utill;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=true&requireSSL=true&verifyServerCertificate=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}