/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;


import com.Inventory.model.OrderItem;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DailyOrdersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionUtil.getCurrentUser(request);
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        // Check user level and redirect if not authorized
        if (!PageAccessUtil.pageRequireLevel(request, response, 2)) {
            return;
        }

        List<OrderItem> orders = new ArrayList<>();
        String alertMessage = "";

        // Database connection details
        String url = "jdbc:mariadb://localhost:3306/pcims";
        String dbUser = "root";
        String password = "";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establish connection
            conn = DriverManager.getConnection(url, dbUser, password);

            String sql = "SELECT oi.orderitemID, oi.orderID, oi.itemID, oi.order_qty, oi.orderStatus, o.orderDate, i.itemName, s.supplierName " +
                         "FROM order_item oi " +
                         "JOIN orders o ON oi.orderID = o.orderID " +
                         "JOIN items i ON oi.itemID = i.itemID " +
                         "JOIN suppliers s ON i.supplierID = s.supplierID " +
                         "WHERE DATE(o.orderDate) = CURDATE() " +
                         "ORDER BY oi.orderitemID";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderitemId(rs.getInt("orderitemID"));
                orderItem.setOrderId(rs.getInt("orderID"));
                orderItem.setItemId(rs.getInt("itemID"));
                orderItem.setOrderQty(rs.getInt("order_qty"));
                orderItem.setOrderStatus(rs.getString("orderStatus"));
                orderItem.setOrderDate(rs.getDate("orderDate"));
                orderItem.setItemName(rs.getString("itemName"));
                orderItem.setSupplierName(rs.getString("supplierName"));
                orders.add(orderItem);
            }

            // Check if orders were found
            if (orders.isEmpty()) {
                alertMessage = "No orders found for today.";
            }

            // Set attributes for JSTL
            request.setAttribute("orders", orders);
            request.setAttribute("alertMessage", alertMessage);

        } catch (SQLException e) {
            e.printStackTrace();
            alertMessage = "An error occurred: " + e.getMessage();
            request.setAttribute("alertMessage", alertMessage);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        request.getRequestDispatcher("/daily_orders.jsp").forward(request, response);
    }
}

