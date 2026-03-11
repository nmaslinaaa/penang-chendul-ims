/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;

import com.Inventory.DAO.UsageItemDAO;
import com.Inventory.model.UsageItem;
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
import java.sql.SQLException;
import java.util.List;

public class ViewItemsUsedServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/pcims";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

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

        List<UsageItem> usageItems = null;
        String messageUsage = null;
        String message = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            usageItems = UsageItemDAO.listAllUsageItems(conn);
            if ( usageItems == null || usageItems.isEmpty()) {
                messageUsage = " No usage items available " ;
            }
            else {
            System.out.println("Usage items retrieved: " + (usageItems != null ? usageItems.size() : "null"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Failed to fetch usage items.";
        }

        request.setAttribute("usage_item", usageItems);
        request.setAttribute("message", message);
        request.setAttribute(messageUsage, messageUsage);
        request.getRequestDispatcher("view_item_used.jsp").forward(request, response);
    }
}


