/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;

import com.Inventory.DAO.ItemDAO;
import com.Inventory.DAO.OrderItemDAO;
import com.Inventory.model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class UpdateOrderStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "";
        String alertClass = "";

        String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String password = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";
        Connection connection = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUser, password);

            int orderItemID = Integer.parseInt(request.getParameter("orderitemID"));
            String newStatus = request.getParameter("status");

            OrderItem orderItem = OrderItemDAO.getOrderItemById(orderItemID, connection);
            if (orderItem == null) {
                message = "Order item not found. Order Item ID: " + orderItemID;
                alertClass = "alert-danger";
            } else {
                String originalStatus = orderItem.getOrderStatus();
                int originalQty = orderItem.getOrderQty();
                int itemID = orderItem.getItemId();

                if (!newStatus.equals(originalStatus)) {
                    boolean updateResult = OrderItemDAO.updateOrderStatus(orderItemID, newStatus, connection);

                    if (updateResult) {
                        if (newStatus.equals("Complete") && !originalStatus.equals("Complete")) {
                            if (ItemDAO.updateItemQuantity(originalQty, itemID, connection)) {
                                message = "Order item updated and item quantity increased.";
                                alertClass = "alert-success";
                            } else {
                                message = "Order item updated but failed to adjust item quantity.";
                                alertClass = "alert-danger";
                            }
                        } else if (newStatus.equals("Incomplete") && originalStatus.equals("Complete")) {
                            if (ItemDAO.updateItemQuantity(-originalQty, itemID, connection)) {
                                message = "Order item updated and item quantity decreased.";
                                alertClass = "alert-success";
                            } else {
                                message = "Order item updated but failed to adjust item quantity.";
                                alertClass = "alert-danger";
                            }
                        } else {
                            message = "Order item updated.";
                            alertClass = "alert-success";
                        }
                    } else {
                        message = "Sorry, failed to update order item!";
                        alertClass = "alert-danger";
                    }
                } else {
                    message = "No changes made.";
                    alertClass = "alert-info";
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + message + "\", \"alertClass\": \"" + alertClass + "\"}");
    }
}
