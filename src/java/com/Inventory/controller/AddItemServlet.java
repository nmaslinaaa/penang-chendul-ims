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
import java.util.Date;
import java.util.List;

public class AddItemServlet extends HttpServlet {
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

        List<Supplier> suppliers = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            suppliers = SupplierDAO.listAllSuppliers(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("all_suppliers", suppliers);
        request.getRequestDispatcher("add_item.jsp").forward(request, response);
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

        String itemName = request.getParameter("item-title");
        String itemMOQ = request.getParameter("item-moq");
        double itemPrice = Double.parseDouble(request.getParameter("item-price")); // Changed to double
        int supplierID = Integer.parseInt(request.getParameter("item-supplier"));
        String currentQty = request.getParameter("item-quantity");

        if (itemName == null || itemMOQ == null || supplierID <= 0 || currentQty == null) {
            request.setAttribute("message", "All fields are required.");
            request.setAttribute("alertClass", "alert-danger");
            doGet(request, response);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check if item already exists
            if (ItemDAO.itemExists(itemName, conn)) {
                request.setAttribute("message", "Item already exists!");
                request.setAttribute("alertClass", "alert-danger");
                doGet(request, response);
                return;
            }

            // Insert new item
            Item newItem = new Item();
            newItem.setItemName(itemName);
            newItem.setMOQ(itemMOQ);
            newItem.setItemPrice(itemPrice); // Set double price
            newItem.setSupplierID(supplierID);
            newItem.setCurrentQty(currentQty);

            if (ItemDAO.insertItem(newItem, conn)) {
                // Insert into user_item table
                newItem.setUserID(user.getUserID());
                newItem.setNewQty(Integer.parseInt(currentQty));
                newItem.setDateAdded(new Date());

                if (ItemDAO.insertUserItem(newItem, conn)) {
                    request.setAttribute("message", "Item added successfully");
                    request.setAttribute("alertClass", "alert-success");
                } else {
                    request.setAttribute("message", "Failed to add user item!");
                    request.setAttribute("alertClass", "alert-danger");
                }
            } else {
                request.setAttribute("message", "Failed to add item!");
                request.setAttribute("alertClass", "alert-danger");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Database error occurred");
            request.setAttribute("alertClass", "alert-danger");
        }

        doGet(request, response);
    }
}
