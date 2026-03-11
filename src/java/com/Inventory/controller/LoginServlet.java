package com.Inventory.controller;

import com.Inventory.DAO.UserDAO;
import com.Inventory.model.User;
import com.Inventory.utill.SessionUtil;

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
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    
    // Check if username or password fields are empty
    if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("usernameError", "This field is required.");
        }
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "This field is required.");
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    // Normalize username to lowercase for case-insensitive login
    String normalizedUsername = username.trim().toLowerCase();

    // Load the JDBC driver for MariaDB
    try {
        Class.forName("org.mariadb.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        LOGGER.log(Level.SEVERE, "JDBC Driver not found", e);
        response.sendRedirect("login.jsp?error=DriverNotFound");
        return;
    }

    // Database URL, username, and password
    String dbUrl = "jdbc:mariadb://localhost:3306/pcims";
    String dbUser = "root";
    String dbPassword = "";

    // Connection to the database
    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        // Retrieve user details based on normalized username and password
        User user = UserDAO.getUserByUsernameAndPassword(normalizedUsername, password, conn);

        // Check if the user exists
        if (user != null) {
            // Check if the user is active
            if (user.getStatus() == 1) {
                // Update last login time
                boolean updated = UserDAO.updateLastLogIn(user.getUserID(), conn);
                if (updated) {
                    LOGGER.log(Level.INFO, "Last login updated successfully for userID: " + user.getUserID());
                } else {
                    LOGGER.log(Level.WARNING, "Failed to update last login for userID: " + user.getUserID());
                }

                // Store the user and user level in the session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("user_level", user.getUser_level());

                // Redirect to admin.jsp
                response.sendRedirect("admin.jsp");
            } else {
                // User is deactivated, redirect back to login.jsp with an error message
                LOGGER.log(Level.WARNING, "User is deactivated: " + normalizedUsername);
                SessionUtil.setErrorMessage(request, "Your account is deactivated. Please contact the manager.");
                response.sendRedirect("login.jsp");
            }
        } else {
            // Invalid username or password, redirect back to login.jsp with an error message
            LOGGER.log(Level.WARNING, "Invalid password: " + normalizedUsername);
            SessionUtil.setErrorMessage(request, "Invalid password. Please re-enter again...");
            response.sendRedirect("login.jsp");
        }
    } catch (SQLException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
        LOGGER.log(Level.SEVERE, "Database error", e);
        SessionUtil.setErrorMessage(request, "Database error. Please try again later.");
        response.sendRedirect("login.jsp");
    }
}

}
