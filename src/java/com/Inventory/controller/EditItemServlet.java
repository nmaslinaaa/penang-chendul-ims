package com.Inventory.controller;

import com.Inventory.DAO.ItemDAO;
import com.Inventory.DAO.SupplierDAO;
import com.Inventory.model.Item;
import com.Inventory.model.Supplier;
import com.Inventory.model.User;
import com.Inventory.utill.PageAccessUtil;
import com.Inventory.utill.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class EditItemServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionUtil.getCurrentUser(request);
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        if (!PageAccessUtil.pageRequireLevel(request, response, 2)) {
            return;
        }

        int itemId = Integer.parseInt(request.getParameter("id"));
        Item item = null;
        List<Supplier> suppliers = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            item = ItemDAO.getItemById(itemId, conn);
            suppliers = SupplierDAO.listAllSuppliers(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("item", item);
        request.setAttribute("all_suppliers", suppliers);
        request.getRequestDispatcher("edit_item.jsp").forward(request, response);
    }

    @Override
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

        int itemId = Integer.parseInt(request.getParameter("id"));
        String itemName = request.getParameter("item-title");
        String itemMOQ = request.getParameter("item-moq");
        double itemPrice = Double.parseDouble(request.getParameter("item-price")); // Changed to double
        int supplierID = Integer.parseInt(request.getParameter("item-supplier"));
        String currentQty = request.getParameter("item-quantity");
        String message = null;
        String alertType = "alert-info";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Fetch the existing item data
            Item existingItem = ItemDAO.getItemById(itemId, conn);

            // Check if any changes have been made
            boolean isChanged = !itemName.equals(existingItem.getItemName()) ||
                                !itemMOQ.equals(existingItem.getMOQ()) ||
                                itemPrice != existingItem.getItemPrice() || // Changed to double comparison
                                supplierID != existingItem.getSupplierID() ||
                                !currentQty.equals(existingItem.getCurrentQty());

            if (isChanged) {
                Item item = new Item(itemId, itemName, itemMOQ, itemPrice, currentQty, supplierID);
                boolean isUpdated = ItemDAO.updateItem(item, conn);
                if (isUpdated) {
                    message = "Successfully Updated The Item";
                    alertType = "alert-success";
                } else {
                    message = "Failed to Update The Item";
                    alertType = "alert-danger";
                }
            } else {
                message = "No changes made to the item.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Database error occurred";
            alertType = "alert-danger";
        }

        request.setAttribute("message", message);
        request.setAttribute("alertType", alertType);
        doGet(request, response); // Reload the form with the updated details
    }
}
