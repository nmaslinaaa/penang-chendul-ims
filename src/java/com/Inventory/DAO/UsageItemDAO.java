/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Inventory.DAO;

import com.Inventory.model.UsageItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsageItemDAO {

    public static List<UsageItem> listAllUsageItems(Connection conn) {
        List<UsageItem> usageItems = new ArrayList<>();
        String sql = "SELECT us.usageitemID, us.usagesID, i.itemID, i.itemName, i.MOQ, us.used_qty, u.usageDate " +
                     "FROM usage_item us " +
                     "LEFT JOIN items i ON us.itemID = i.itemID " +
                     "LEFT JOIN usages u ON us.usagesID = u.usagesID " +
                     "ORDER BY us.usageitemID DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UsageItem usageItem = new UsageItem();
                usageItem.setUsageitemID(rs.getInt("usageitemID"));
                usageItem.setUsagesID(rs.getInt("usagesID"));
                usageItem.setItemID(rs.getInt("itemID"));
                usageItem.setItemName(rs.getString("itemName"));
                usageItem.setMOQ(rs.getString("MOQ"));
                usageItem.setUsedQty(rs.getInt("used_qty"));
                usageItem.setUsageDate(rs.getDate("usageDate"));
                usageItems.add(usageItem);
            }
            System.out.println("Items retrieved in DAO: " + usageItems.size()); // Debug statement
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return usageItems;
    }
}

