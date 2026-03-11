
package com.Inventory.DAO;

import com.Inventory.model.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    public static List<OrderItem> findRecentOrderAdded(int limit, Connection conn) throws SQLException {
        String sql = "SELECT s.orderitemID, s.order_qty, o.orderDate, p.itemName, p.itemPrice "
                + "FROM order_item s "
                + "JOIN orders o ON s.orderID = o.orderID "
                + "LEFT JOIN items p ON s.itemID = p.itemID "
                + "ORDER BY o.orderDate DESC "
                + "LIMIT ?";

        List<OrderItem> orders = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderitemId(rs.getInt("orderitemID"));
                    orderItem.setOrderQty(rs.getInt("order_qty"));
                    orderItem.setOrderDate(rs.getDate("orderDate"));
                    orderItem.setItemName(rs.getString("itemName"));
                    orderItem.setItemPrice(rs.getDouble("itemPrice")); 
                    orders.add(orderItem);
                }
            }
        }
        return orders;
    }

        public static List<OrderItem> listAllOrderItems(Connection conn) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>(); // initialize to avoid null
        String sql = "SELECT oi.orderitemID, oi.orderID, oi.itemID, oi.order_qty, oi.orderStatus, o.orderDate, i.itemName, i.itemPrice " +
                     "FROM order_item oi " +
                     "LEFT JOIN items i ON oi.itemID = i.itemID " +
                     "JOIN orders o ON oi.orderID = o.orderID " +
                     "ORDER BY oi.orderID DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderitemId(rs.getInt("orderitemID"));
                orderItem.setOrderId(rs.getInt("orderID"));
                orderItem.setItemId(rs.getInt("itemID"));
                orderItem.setOrderQty(rs.getInt("order_qty"));
                orderItem.setOrderStatus(rs.getString("orderStatus"));
                orderItem.setOrderDate(rs.getDate("orderDate"));
                orderItem.setItemName(rs.getString("itemName"));
                orderItem.setItemPrice(rs.getDouble("itemPrice"));
                orderItems.add(orderItem);
            }
        }
        return orderItems; // Return an empty list instead of null
    }

    public static boolean insertOrderItem(OrderItem orderItem, Connection conn) throws SQLException {
        String sql = "INSERT INTO order_item (orderID, itemID, order_qty, orderStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getItemId());
            stmt.setInt(3, orderItem.getOrderQty());
            stmt.setString(4, orderItem.getOrderStatus());
            return stmt.executeUpdate() > 0;
        }
    }

    public static void addOrderItem(int orderID, int itemID, int quantity, Connection conn) throws SQLException {
        String sql = "INSERT INTO order_item (orderID, itemID, order_qty, orderStatus) VALUES (?, ?, ?, 'Incomplete')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            stmt.setInt(2, itemID);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        }
    }

    public static List<OrderItem> findAllOrders(Connection conn) throws SQLException {
        String sql = "SELECT o.orderID, s.orderitemID, s.order_qty, o.orderDate, p.itemName, p.itemPrice, s.orderStatus " +
                     "FROM order_item s " +
                     "JOIN orders o ON o.orderID = s.orderID " +
                     "LEFT JOIN items p ON s.itemID = p.itemID " +
                     "ORDER BY o.orderDate DESC, o.orderID DESC";
        List<OrderItem> orderItems = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(rs.getInt("orderID"));
                orderItem.setOrderitemId(rs.getInt("orderitemID"));
                orderItem.setOrderQty(rs.getInt("order_qty"));
                orderItem.setOrderDate(rs.getDate("orderDate"));
                orderItem.setItemName(rs.getString("itemName"));
                orderItem.setItemPrice(rs.getDouble("itemPrice")); 
                orderItem.setOrderStatus(rs.getString("orderStatus"));
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

    public static boolean updateOrderStatus(int orderItemID, String status, Connection conn) throws SQLException {
        String sql = "UPDATE order_item SET orderStatus = ? WHERE orderitemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderItemID);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean deleteOrderItem(int orderItemID, Connection conn) throws SQLException {
        String sql = "DELETE FROM order_item WHERE orderitemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemID);
            return stmt.executeUpdate() > 0;
        }
    }

    public static OrderItem getOrderItemById(int orderItemID, Connection conn) throws SQLException {
        String sql = "SELECT oi.orderitemID, oi.orderID, oi.itemID, oi.order_qty, oi.orderStatus, o.orderDate, i.itemName, i.itemPrice " +
                     "FROM order_item oi " +
                     "LEFT JOIN items i ON oi.itemID = i.itemID " +
                     "JOIN orders o ON oi.orderID = o.orderID " +
                     "WHERE oi.orderitemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItemID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderitemId(rs.getInt("orderitemID"));
                    orderItem.setOrderId(rs.getInt("orderID"));
                    orderItem.setItemId(rs.getInt("itemID"));
                    orderItem.setOrderQty(rs.getInt("order_qty"));
                    orderItem.setOrderStatus(rs.getString("orderStatus"));
                    orderItem.setOrderDate(rs.getDate("orderDate"));
                    orderItem.setItemName(rs.getString("itemName"));
                    orderItem.setItemPrice(rs.getDouble("itemPrice")); 
                    return orderItem;
                }
            }
        }
        return null;
    }

    public static OrderItem findOrderItemById(int orderId, int itemId, Connection conn) throws SQLException {
        String sql = "SELECT oi.orderitemID, oi.orderID, oi.itemID, oi.order_qty, oi.orderStatus, o.orderDate, i.itemName, i.itemPrice " +
                     "FROM order_item oi " +
                     "LEFT JOIN items i ON oi.itemID = i.itemID " +
                     "JOIN orders o ON oi.orderID = o.orderID " +
                     "WHERE oi.orderID = ? AND oi.itemID = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderitemId(rs.getInt("orderitemID"));
                    orderItem.setOrderId(rs.getInt("orderID"));
                    orderItem.setItemId(rs.getInt("itemID"));
                    orderItem.setOrderQty(rs.getInt("order_qty"));
                    orderItem.setOrderStatus(rs.getString("orderStatus"));
                    orderItem.setOrderDate(rs.getDate("orderDate"));
                    orderItem.setItemName(rs.getString("itemName"));
                    orderItem.setItemPrice(rs.getDouble("itemPrice")); 
                    return orderItem;
                }
            }
        }
        return null;
    }
}


