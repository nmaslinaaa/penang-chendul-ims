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
import java.util.regex.Pattern;

public class ChangePasswordServlet extends HttpServlet {

    // Regular expression for password validation
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

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
        String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=true&requireSSL=true&verifyServerCertificate=false";
        String dbUser = "root";
        String dbPassword = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";

        String oldPassword = request.getParameter("old-password");
        String newPassword = request.getParameter("new-password");

        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            // Validate the new password against the rules
            if (!Pattern.matches(PASSWORD_REGEX, newPassword)) {
                message = "New password must contain at least 8 characters, including an uppercase letter, a lowercase letter, a number, and a special character.";
                alertClass = "alert-danger";
            } else {
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(url, dbUser, dbPassword);

                    // Verify old password
                    boolean isOldPasswordCorrect = UserDAO.verifyPassword(currentUser.getUserID(), oldPassword, conn);
                    if (isOldPasswordCorrect) {
                        // Update to new password
                        boolean updateResult = UserDAO.updateUserPassword(currentUser.getUserID(), newPassword, conn);
                        if (updateResult) {
                            message = "Password changed successfully.";
                            alertClass = "alert-success";
                        } else {
                            message = "Failed to change password.";
                            alertClass = "alert-danger";
                        }
                    } else {
                        message = "Old password is incorrect.";
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
            message = "Old password and new password cannot be empty.";
            alertClass = "alert-danger";
        }

        request.setAttribute("message", message);
        request.setAttribute("alertClass", alertClass);
        request.getRequestDispatcher("/change_password.jsp").forward(request, response);
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

        request.getRequestDispatcher("/change_password.jsp").forward(request, response);
    }
}
