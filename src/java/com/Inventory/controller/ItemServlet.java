/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;

import com.Inventory.DAO.ItemDAO;
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


public class ItemServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=true&requireSSL=true&verifyServerCertificate=false";
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

        String message = null;
        List<Item> items = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            items = ItemDAO.listAllItems(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Failed to fetch items";
        }

        request.setAttribute("items", items);
        request.setAttribute("message", message);
        request.getRequestDispatcher("item.jsp").forward(request, response);
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

        String action = request.getParameter("action");
        String message = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if ("delete".equals(action)) {
                int itemID = Integer.parseInt(request.getParameter("item-id"));
                boolean isDeleted = ItemDAO.deleteItem(itemID, conn);
                if (isDeleted) {
                    message = "Successfully Deleted The Item";
                } else {
                    message = "Failed to Delete The Item";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Database error occurred";
        }

        List<Item> items = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            items = ItemDAO.listAllItems(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("items", items);
        request.setAttribute("message", message);
        request.getRequestDispatcher("item.jsp").forward(request, response);
    }
}
