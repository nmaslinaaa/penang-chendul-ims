/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;

import com.Inventory.DAO.ItemDAO;
import com.Inventory.DAO.OrderDAO;
import com.Inventory.DAO.OrderItemDAO;
import com.Inventory.model.Item;
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


public class AddOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionUtil.getCurrentUser(request);
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        // Check user level and redirect if not authorized
        if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
            return;
        }

        String message = "";
        String alertClass = "";

        List<Item> itemsList = null;

        String url = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
        String dbUser = "root";
        String password = "BCptazPhAsXTGhZpcFQuRtWyUjWsujjN";
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUser, password);

            // Load all items for the dropdown
            itemsList = ItemDAO.listAllItems(connection);

            String date = request.getParameter("date");
            String[] items = request.getParameterValues("items[]");
            String[] quantities = request.getParameterValues("quantities[]");

            // Check if an order already exists for the current date
            boolean orderExists = OrderDAO.findExistingOrderForDate(date, connection);
            if (orderExists) {
                message = "An order has already been placed today.";
                alertClass = "alert-danger";
            } else {
                // Insert the order into the database
                int userID = user.getUserID();
                int orderID = OrderDAO.addOrder(date, userID, connection);

                if (orderID > 0) {
                    for (int i = 0; i < items.length; i++) {
                        String itemName = items[i];
                        int quantity = Integer.parseInt(quantities[i]);

                        Item item = ItemDAO.getItemByName(itemName, connection);
                        if (item != null) {
                            int itemID = item.getItemID();
                            OrderItemDAO.addOrderItem(orderID, itemID, quantity, connection);
                        } else {
                            message = "Item not found: " + itemName;
                            alertClass = "alert-danger";
                            break;
                        }
                    }
                    if (message.isEmpty()) {
                        message = "Order added successfully.";
                        alertClass = "alert-success";
                    }
                } else {
                    message = "Failed to add order.";
                    alertClass = "alert-danger";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred: " + e.getMessage();
            alertClass = "alert-danger";
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("message", message);
        request.setAttribute("alertClass", alertClass);
        request.setAttribute("itemsList", itemsList);

        request.getRequestDispatcher("/add_order.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionUtil.getCurrentUser(request);
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        // Check user level and redirect if not authorized
        if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
            return;
        }

        List<Item> itemsList = null;

        String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String password = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUser, password);

            // Load all items for the dropdown
            itemsList = ItemDAO.listAllItems(connection);
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
        request.setAttribute("itemsList", itemsList);
        request.getRequestDispatcher("/add_order.jsp").forward(request, response);
    }
}

