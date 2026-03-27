/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;


import com.Inventory.DAO.UserDAO;
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


public class EditAccountServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = SessionUtil.getCurrentUser(request);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        // Check user level and redirect if not authorized
        if (!PageAccessUtil.pageRequireLevel(request, response, 3)) {
            return;
        }

        String message = "";
        String alertClass = "";

        // Database connection details
        String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String dbPassword = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";

        String name = request.getParameter("name");
        String username = request.getParameter("username");

        if (name != null && !name.isEmpty() && username != null && !username.isEmpty()) {
            if (name.equals(currentUser.getName()) && username.equals(currentUser.getUsername())) {
                message = "No changes were made.";
                alertClass = "alert-info";
            } else if (username.equals(username)) {
                message = "This username is already in use. Please choose a different username.";
                alertClass = "alert-danger";
            }else {
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(url, dbUser, dbPassword);
                    currentUser.setName(name);
                    currentUser.setUsername(username);

                    boolean updateResult = UserDAO.updateUserDetails(currentUser, conn);
                    if (updateResult) {
                        message = "Account updated successfully.";
                        alertClass = "alert-success";
                        SessionUtil.setCurrentUser(request, currentUser); // Update session with new details
                    } else {
                        message = "Failed to update account.";
                        alertClass = "alert-danger";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    message = "An error occurred: " + e.getMessage();
                    alertClass = "alert-danger";
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            message = "Name and Username cannot be empty.";
            alertClass = "alert-danger";
        }
        request.setAttribute("message", message);
        request.setAttribute("alertClass", alertClass);
        request.getRequestDispatcher("/edit_account.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = SessionUtil.getCurrentUser(request);
        if (currentUser == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        // Check user level and redirect if not authorized
        if (!PageAccessUtil.pageRequireLevel(request, response, 3)) {
            return;
        }

        request.getRequestDispatcher("/edit_account.jsp").forward(request, response);
    }
}

