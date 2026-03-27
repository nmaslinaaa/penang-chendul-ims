/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;

import com.Inventory.DAO.OrderItemDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DeleteOrderItemServlet extends HttpServlet {
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

            boolean deleteResult = OrderItemDAO.deleteOrderItem(orderItemID, connection);

            if (deleteResult) {
                message = "Order Item Successfully Deleted.";
                alertClass = "alert-success";
            } else {
                message = "Failed to delete order item.";
                alertClass = "alert-danger";
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

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("alertClass", alertClass);

        response.sendRedirect("orders.jsp");
    }
}
