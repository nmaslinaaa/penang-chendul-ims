package com.Inventory.controller;

import com.Inventory.DAO.UserDAO;
import com.Inventory.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;  // untuk character password

public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("full-name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userLevel = request.getParameter("level");
        
        String cancel = request.getParameter("cancel");
            if ("cancel".equals(cancel)) {
                handleCancelAction(request, response);
                return;
            }


        // Validate input
        if (fullName == null || fullName.isEmpty() ||
            username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            userLevel == null || userLevel.isEmpty()) {
            response.sendRedirect("add_user.jsp?error=MissingFields");
            return;
        }
        
        // Validate username format
        if (!isValidUsername(username)) {
            response.sendRedirect("add_user.jsp?error=Username can only contain alphabets(uppercase and lowercase) and number(0 - 9)\n" +
"    </p>");
            return;
        }
        
        // Validate password strength
        if (!isValidPassword(password)) {
            response.sendRedirect("add_user.jsp?error=Password must be at least 8 characters long, include at least one uppercase letter, one number, and one special character.\n" +
"    </p>");
            return;
        }
        

        // Hash the password using SHA-1
        String hashedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passwordBytes = password.getBytes("UTF-8");
            byte[] hashBytes = md.digest(passwordBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            hashedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("add_user.jsp?error=HashingError");
            return;
        }

        // Database URL, username, and password
        String dbUrl = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
        String dbUser = "root";
        String dbPassword = "BCptazPhAsXTGhZpcFQuRtWyUjWsujjN";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // Check if a user with the same full name exists
            boolean isUsernameTaken = UserDAO.isUsernameTaken(username, conn); 
            if (UserDAO.isUsernameTaken(username, conn)) {
                response.sendRedirect("add_user.jsp?error=This username is already in use. Please choose a different username.");
                return;
            }

            // Check if a user with the same hashed password exists
            boolean isPasswordTaken = UserDAO.isPasswordTaken(hashedPassword, conn);
            if (isPasswordTaken) {
                response.sendRedirect("add_user.jsp?error=This password is already in use. Please choose a different password.");
                return;
            }

            // Add the new user
            User user = new User();
            user.setName(fullName);
            user.setUsername(username);
            user.setPassword(hashedPassword);
            user.setUser_level(Integer.parseInt(userLevel));
            user.setStatus(1); // Default status as active

            boolean userAdded = UserDAO.addUser(user, conn);
            if (userAdded) {
                response.sendRedirect("add_user.jsp?success=UserCreated");
            } else {
                response.sendRedirect("add_user.jsp?error=DatabaseError");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("add_user.jsp?error=DatabaseError");
        }
        
    }
    
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.{8,}).*$";
        return Pattern.matches(passwordPattern, password);
    }

    private boolean isValidUsername(String username) {
        String usernamePattern = "^[a-zA-Z0-9._]+$"; // Allows alphabets, numbers, underscores, and dots
        return Pattern.matches(usernamePattern, username);
    }
    
    private void handleCancelAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
       // Redirect back to the previous page or a default page
       String referer = request.getHeader("Referer");
       if (referer != null) {
           response.sendRedirect(referer); // Redirects to the previous page based on HTTP Referer header
       } else {
           response.sendRedirect("users.jsp"); // Fallback to users page if referer is unavailable
    }
}
}
    

