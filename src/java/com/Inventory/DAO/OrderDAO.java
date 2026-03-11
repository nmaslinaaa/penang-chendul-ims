
package com.Inventory.DAO;

import com.Inventory.model.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static int countByOrderId(Connection conn) {
        String sql = "SELECT COUNT(orderID) AS total FROM orders";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Order> findOrderByDates(String startDate, String endDate, Connection conn) throws SQLException {
        String sql = "SELECT o.orderID, o.orderDate, i.itemName, s.supplierName, oi.orderQty, u.name AS placedBy, oi.orderStatus" +
                     "FROM orders o " +
                     "JOIN order_item oi ON o.orderID = oi.orderID " +
                     "JOIN items i ON oi.itemID = i.itemID " +
                     "JOIN suppliers s ON i.supplierID = s.supplierID " +
                     "JOIN users u ON o.userID = u.userID " +
                     "WHERE o.orderDate BETWEEN ? AND ? " +
                     "ORDER BY o.orderDate DESC";

        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("orderID"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setItemName(rs.getString("itemName"));
                order.setSupplierName(rs.getString("supplierName"));
                order.setOrderQty(rs.getInt("orderQty"));
                order.setPlacedBy(rs.getString("placedBy"));
                order.setOrderStatus(rs.getString("orderStatus"));
                orders.add(order);
            }
        }
        return orders;
    }

    public static boolean insertOrder(Order order, Connection conn) throws SQLException {
        String sql = "INSERT INTO orders (orderDate, userID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, (Date) order.getOrderDate());
            stmt.setInt(2, order.getUserID());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting order failed, no ID obtained.");
                }
            }
            return true;
        }
    }

    public static boolean findExistingOrderForDate(Date date, Connection conn) throws SQLException {
        String sql = "SELECT orderID FROM orders WHERE orderDate = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public static boolean findExistingOrderForDate(String date, Connection conn) throws SQLException {
        String query = "SELECT orderID FROM orders WHERE DATE(orderDate) = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static int addOrder(String date, int userID, Connection conn) throws SQLException {
        String query = "INSERT INTO orders (orderDate, userID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, date);
            stmt.setInt(2, userID);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }
}
