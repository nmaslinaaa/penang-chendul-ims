package com.Inventory.DAO;

import com.Inventory.model.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public static int countByItemId(Connection conn) {
        String sql = "SELECT COUNT(itemID) AS total FROM items";
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

    public static List<Item> findRecentItemAdded(int limit, Connection conn) {
        String sql = "SELECT p.itemID, p.itemName, p.itemPrice, p.current_qty, c.supplierName AS supplierName  " +
                     "FROM items p " +
                     "LEFT JOIN suppliers c ON c.supplierID = p.supplierID " +
                     "ORDER BY p.itemID DESC LIMIT ?";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setItemPrice(rs.getDouble("itemPrice")); // Change to double
                    item.setItemID(rs.getInt("itemID"));
                    item.setItemName(rs.getString("itemName"));
                    item.setCurrentQty(rs.getString("current_qty"));
                    item.setSupplierName(rs.getString("supplierName"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<Item> listAllItems(Connection conn) {
        String sql = "SELECT p.itemID, p.itemName, p.MOQ, p.itemPrice, p.current_qty, c.supplierName, ui.date_added, u.username " +
                     "FROM items p " +
                     "LEFT JOIN user_item ui ON ui.itemID = p.itemID " +
                     "LEFT JOIN users u ON u.userID = ui.userID " +
                     "LEFT JOIN suppliers c ON c.supplierID = p.supplierID " +
                     "ORDER BY p.itemID DESC";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setItemName(rs.getString("itemName"));
                item.setMOQ(rs.getString("MOQ"));  // Assuming MOQ is an integer
                item.setItemPrice(rs.getDouble("itemPrice")); // Change to double
                item.setCurrentQty(rs.getString("current_qty"));  // Assuming current_qty is an integer
                item.setSupplierName(rs.getString("supplierName"));
                item.setDateAdded(rs.getDate("date_added"));  // Assuming date_added is a date
                item.setUsername(rs.getString("username"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static boolean deleteItem(int itemID, Connection conn) {
        String sql = "DELETE FROM items WHERE itemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean itemExists(String itemName, Connection conn) throws SQLException {
        String sql = "SELECT itemID FROM items WHERE itemName = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, itemName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static boolean insertItem(Item item, Connection conn) throws SQLException {
        String sql = "INSERT INTO items (itemName, MOQ, itemPrice, supplierID, current_qty) VALUES (?,?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getMOQ());
            stmt.setDouble(3, item.getItemPrice()); // Change to double
            stmt.setInt(4, item.getSupplierID());
            stmt.setString(5, item.getCurrentQty());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setItemID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Inserting item failed, no ID obtained.");
                }
            }
            return true;
        }
    }

    public static boolean insertUserItem(Item item, Connection conn) throws SQLException {
        String sql = "INSERT INTO user_item (userID, itemID, new_qty, date_added) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getUserID());
            stmt.setInt(2, item.getItemID());
            stmt.setInt(3, item.getNewQty());
            stmt.setDate(4, new java.sql.Date(item.getDateAdded().getTime()));

            return stmt.executeUpdate() > 0;
        }
    }

    private static boolean deleteUserItem(int itemID, Connection conn) throws SQLException {
        String sql = "DELETE FROM user_item WHERE itemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemID);
            return stmt.executeUpdate() > 0;
        }
    }

    public static Item getItemById(int itemId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM items WHERE itemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setItemID(rs.getInt("itemID"));
                    item.setItemName(rs.getString("itemName"));
                    item.setMOQ(rs.getString("MOQ"));
                    item.setItemPrice(rs.getDouble("itemPrice")); // Change to double
                    item.setCurrentQty(rs.getString("current_qty"));
                    item.setSupplierID(rs.getInt("supplierID"));
                    return item;
                }
            }
        }
        return null;
    }

    public static boolean updateItem(Item item, Connection conn) throws SQLException {
        String sql = "UPDATE items SET itemName = ?, MOQ = ?,  itemPrice = ?, supplierID = ?, current_qty = ? WHERE itemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getMOQ());
            stmt.setDouble(3, item.getItemPrice()); // Change to double
            stmt.setInt(4, item.getSupplierID());
            stmt.setString(5, item.getCurrentQty());
            stmt.setInt(6, item.getItemID());
            return stmt.executeUpdate() > 0;
        }
    }

    public static Item getItemByName(String itemName, Connection conn) throws SQLException {
        String sql = "SELECT * FROM items WHERE itemName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, itemName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setItemID(rs.getInt("itemID"));
                    item.setItemName(rs.getString("itemName"));
                    item.setMOQ(rs.getString("MOQ"));
                    item.setItemPrice(rs.getDouble("itemPrice")); 
                    item.setCurrentQty(rs.getString("current_qty"));
                    item.setSupplierID(rs.getInt("supplierID"));
                    return item;
                }
            }
        }
        return null;
    }

    public static boolean updateItemQuantity(int qtyDifference, int itemId, Connection conn) throws SQLException {
        String sql = "UPDATE items SET current_qty = current_qty + ? WHERE itemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, qtyDifference);
            stmt.setInt(2, itemId);
            return stmt.executeUpdate() > 0;
        }
    }
    
     // Method to get the current quantity of an item by itemID
    public static int getCurrentQuantity(int itemId, Connection conn) throws SQLException {
        String sql = "SELECT current_qty FROM items WHERE itemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Integer.parseInt(rs.getString("current_qty"));
                }
            }
        }
        return 0; // return 0 if the item doesn't exist
    }

    // Method to insert a used quantity record into the usage_item table
    public static boolean insertUsageItem(int usagesID, int itemID, int usedQty, Connection conn) throws SQLException {
        String sql = "INSERT INTO usage_item (usagesID, itemID, used_qty) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usagesID);
            stmt.setInt(2, itemID);
            stmt.setInt(3, usedQty);
            return stmt.executeUpdate() > 0;
        }
    }

    // Method to update current quantity of an item in items table
    public static boolean updateCurrentQuantity(int itemId, int newQty, Connection conn) throws SQLException {
        String sql = "UPDATE items SET current_qty = ? WHERE itemID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, String.valueOf(newQty));
            stmt.setInt(2, itemId);
            return stmt.executeUpdate() > 0;
        }
    }

}
