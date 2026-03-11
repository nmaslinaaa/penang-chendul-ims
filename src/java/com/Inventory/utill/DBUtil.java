///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.Inventory.utill;
//
///**
// *
// * @author User
// */
//import java.sql.*;
//import java.util.*;
//
//public class DBUtil {
//
//    private static final String DB_URL = "jdbc:mariadb://localhost:3306/system";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "";
//
//    // Get database connection
//    public static Connection getConnection() {
//        Connection conn = null;
//        try {
//            Class.forName("org.mariadb.jdbc.Driver");
//            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }
//
//    // Count records by supplier ID
//    public static int countBySupplierId(Connection conn) {
//        String query = "SELECT COUNT(*) AS total FROM suppliers";
//        return executeCountQuery(conn, query);
//    }
//
//    // Count records by item ID
//    public static int countByItemId(Connection conn) {
//        String query = "SELECT COUNT(*) AS total FROM items";
//        return executeCountQuery(conn, query);
//    }
//
//    // Count records by order ID
//    public static int countByOrderId(Connection conn) {
//        String query = "SELECT COUNT(*) AS total FROM orders";
//        return executeCountQuery(conn, query);
//    }
//
//    // Count records by user ID
//    public static int countByUserId(Connection conn) {
//        String query = "SELECT COUNT(*) AS total FROM users";
//        return executeCountQuery(conn, query);
//    }
//
//    // Execute count query
//    private static int executeCountQuery(Connection conn, String query) {
//        int count = 0;
//        try (Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            if (rs.next()) {
//                count = rs.getInt("total");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }
//
//    // Find recent items added
//    public static List<Map<String, Object>> findRecentItemsAdded(Connection conn, int limit) {
//        List<Map<String, Object>> items = new ArrayList<>();
//        String query = "SELECT * FROM items ORDER BY date_added DESC LIMIT ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, limit);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Map<String, Object> item = new HashMap<>();
//                item.put("itemID", rs.getInt("itemID"));
//                item.put("itemName", rs.getString("itemName"));
//                item.put("current_qty", rs.getInt("current_qty"));
//                item.put("supplier", rs.getString("supplier"));
//                items.add(item);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return items;
//    }
//
//    // Find recent orders added
//    public static List<Map<String, Object>> findRecentOrdersAdded(Connection conn, int limit) {
//        List<Map<String, Object>> orders = new ArrayList<>();
//        String query = "SELECT * FROM orders ORDER BY orderDate DESC LIMIT ?";
//        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, limit);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Map<String, Object> order = new HashMap<>();
//                order.put("orderitemID", rs.getInt("orderitemID"));
//                order.put("itemName", rs.getString("itemName"));
//                order.put("orderDate", rs.getDate("orderDate"));
//                order.put("order_qty", rs.getInt("order_qty"));
//                orders.add(order);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return orders;
//    }
//}
//
