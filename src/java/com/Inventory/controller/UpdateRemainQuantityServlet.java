package com.Inventory.controller;

import com.Inventory.DAO.ItemDAO;
import com.Inventory.model.Item;
import com.Inventory.model.User;
import com.Inventory.utill.PageAccessUtil;
import com.Inventory.utill.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class UpdateRemainQuantityServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionUtil.getCurrentUser(request);
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        if (!PageAccessUtil.pageRequireLevel(request, response, 2)) {
            return;
        }

        String message = "";
        String alertClass = "";
        String[] items = request.getParameterValues("items[]");
        String[] remainQuantities = request.getParameterValues("quantities[]");
        String date = request.getParameter("date");

        // Create a set to track unique items
        HashSet<Integer> uniqueItems = new HashSet<>();
        for (String item : items) {
            uniqueItems.add(Integer.parseInt(item));
        }

        // Check for duplicate items
        if (uniqueItems.size() < items.length) {
            message = "Duplicate items are not allowed. Please select different items.";
            alertClass = "alert-danger";
            request.setAttribute("message", message);
            request.setAttribute("alertClass", alertClass);
            request.setAttribute("itemsList", ItemDAO.listAllItems(null)); // Set the item list to prevent it from being empty
            request.getRequestDispatcher("/update_remain_quantity.jsp").forward(request, response);
            return;
        }

        // Check for negative quantities
        for (String quantity : remainQuantities) {
            int remainQty = Integer.parseInt(quantity);
            if (remainQty < 0) {
                message = "Quantities cannot be negative. Please enter valid numbers.";
                alertClass = "alert-danger";
                request.setAttribute("message", message);
                request.setAttribute("alertClass", alertClass);
                request.setAttribute("itemsList", ItemDAO.listAllItems(null)); // Set the item list to prevent it from being empty
                request.getRequestDispatcher("/update_remain_quantity.jsp").forward(request, response);
                return;
            }
        }

        String url = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
        String dbUser = "root";
        String password = "BCptazPhAsXTGhZpcFQuRtWyUjWsujjN";
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUser, password);
            connection.setAutoCommit(false); // Begin transaction

            // Step 1: Check if an update for today already exists
            String sqlCheckUsage = "SELECT COUNT(*) FROM usages WHERE usageDate = ?";
            try (PreparedStatement psCheckUsage = connection.prepareStatement(sqlCheckUsage)) {
                psCheckUsage.setString(1, date);
                ResultSet rs = psCheckUsage.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    message = "You have already updated the quantities today.";
                    alertClass = "alert-danger";
                    request.setAttribute("message", message);
                    request.setAttribute("alertClass", alertClass);
                    request.setAttribute("items", items); // Preserve selected items
                    request.setAttribute("quantities", remainQuantities); // Preserve quantities
                    request.setAttribute("itemsList", ItemDAO.listAllItems(connection)); // Set items list
                    request.getRequestDispatcher("/update_remain_quantity.jsp").forward(request, response);
                    return;
                }
            }

            // Step 2: Insert usage record
            String sqlInsertUsage = "INSERT INTO usages (usageDate, userID) VALUES (?, ?)";
            int usagesID = 0;

            try (PreparedStatement psInsertUsage = connection.prepareStatement(sqlInsertUsage, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psInsertUsage.setString(1, date);
                psInsertUsage.setInt(2, user.getUserID());
                psInsertUsage.executeUpdate();

                // Get the generated usagesID
                try (ResultSet generatedKeys = psInsertUsage.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usagesID = generatedKeys.getInt(1);
                    }
                }
            }

            // Process items
            for (int i = 0; i < items.length; i++) {
                int itemId = Integer.parseInt(items[i]);
                int remainQty = Integer.parseInt(remainQuantities[i]);

                // Retrieve current quantity
                String sqlSelect = "SELECT current_qty FROM items WHERE itemID = ?";
                try (PreparedStatement psSelect = connection.prepareStatement(sqlSelect)) {
                    psSelect.setInt(1, itemId);
                    ResultSet rs = psSelect.executeQuery();

                    int currentQty = 0;
                    if (rs.next()) {
                        currentQty = Integer.parseInt(rs.getString("current_qty"));
                    }

                    // Calculate used quantity
                    int usedQty = currentQty - remainQty;

                    // Insert into usage_item
                    String sqlInsertUsageItem = "INSERT INTO usage_item (usagesID, itemID, used_qty) VALUES (?, ?, ?)";
                    try (PreparedStatement psInsertUsageItem = connection.prepareStatement(sqlInsertUsageItem)) {
                        psInsertUsageItem.setInt(1, usagesID);
                        psInsertUsageItem.setInt(2, itemId);
                        psInsertUsageItem.setInt(3, usedQty);
                        psInsertUsageItem.executeUpdate();
                    }

                    // Update current quantity
                    String sqlUpdate = "UPDATE items SET current_qty = ? WHERE itemID = ?";
                    try (PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate)) {
                        psUpdate.setString(1, String.valueOf(currentQty - usedQty));
                        psUpdate.setInt(2, itemId);
                        psUpdate.executeUpdate();
                    }
                }
            }

            connection.commit(); // Commit transaction
            message = "Quantities updated successfully.";
            alertClass = "alert-success";
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred: " + e.getMessage();
            alertClass = "alert-danger";
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback if any error occurs
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Set attributes for the JSP
        request.setAttribute("message", message);
        request.setAttribute("alertClass", alertClass);
        request.setAttribute("itemsList", ItemDAO.listAllItems(connection));

        // Forward to JSP
        request.getRequestDispatcher("/update_remain_quantity.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionUtil.getCurrentUser(request);
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        // Get items for dropdown
        Connection connection = null;
        try {
            String url = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
            String dbUser = "root";
            String password = "";
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUser, password);

            List<Item> itemsList = ItemDAO.listAllItems(connection);
            request.setAttribute("itemsList", itemsList);
            request.getRequestDispatcher("/update_remain_quantity.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
