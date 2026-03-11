/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;

import com.Inventory.DAO.UserDAO;
import static com.Inventory.DAO.UserDAO.isPasswordTaken;
import com.Inventory.model.User;
import com.Inventory.model.UserGroup;
import com.Inventory.model.DBConnection;
import com.Inventory.utill.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class UserController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            try {
                updateUser(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("update-pass".equals(action)) {
            try {
                updatePassword(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("cancel".equals(action)) {
        handleCancelAction(request, response);
    }
}
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            handleEditUserRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleEditUserRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String userIdParam = request.getParameter("userID");
        if (userIdParam == null || userIdParam.isEmpty()) {
            response.sendRedirect("users.jsp");
            return;
        }

        int userID = Integer.parseInt(userIdParam);
        Connection conn = DBConnection.getConnection();
        UserDAO userDAO = new UserDAO();
        User e_user = null;
        List<UserGroup> groups = null;

        try {
            e_user = userDAO.findByUserId(userID, conn);
            groups = userDAO.findAllUserGroups(conn);

            if (e_user == null) {
                response.sendRedirect("users.jsp");
                return;
            }

            request.setAttribute("e_user", e_user);
            request.setAttribute("groups", groups);
            request.getRequestDispatcher("edit_user.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    int userID = Integer.parseInt(request.getParameter("userID"));
    String name = request.getParameter("name");
    String username = request.getParameter("username");
    int level = Integer.parseInt(request.getParameter("level"));
    int status = Integer.parseInt(request.getParameter("status"));

    Connection conn = DBConnection.getConnection();
    UserDAO userDAO = new UserDAO();

    try {
        User existingUser = userDAO.findByUserId(userID, conn);
        String msg = "No change has been made.";
        String alertClass = "alert-info";

        // Check if the username is updated and already taken by another user
        if (!existingUser.getUsername().equals(username) && userDAO.isUsernameTaken(username, conn)) {
            msg = "The username is already taken by another user.";
            alertClass = "alert-danger";
        } else {
            boolean isUpdated = false;
            if (!existingUser.getName().equals(name) || existingUser.getUser_level() != level || existingUser.getStatus() != status) {
                User user = new User();
                user.setUserID(userID);
                user.setName(name);
                user.setUsername(username);
                user.setUser_level(level);
                user.setStatus(status);

                isUpdated = userDAO.updateUser(user, conn);

                if (isUpdated) {
                    msg = "User updated successfully.";
                    alertClass = "alert-success";
                } else {
                    msg = "Failed to update user.";
                    alertClass = "alert-danger";
                }
            }
        }

        request.setAttribute("msg", msg);
        request.setAttribute("alertClass", alertClass);
        handleEditUserRequest(request, response); // Forward back to edit page with message
    } catch (SQLException e) {
        e.printStackTrace();
        request.setAttribute("msg", "An error occurred: " + e.getMessage());
        request.setAttribute("alertClass", "alert-danger");
        handleEditUserRequest(request, response); // Forward back to edit page with message
    } finally {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
    int userID = Integer.parseInt(request.getParameter("userID")); // Get the current user ID
    String newPassword = request.getParameter("newPassword"); // New password from the request

            // Hash the password using SHA-1
        String hashedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passwordBytes = newPassword.getBytes("UTF-8");
            byte[] hashBytes = md.digest(passwordBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            hashedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("edit_user.jsp?error=HashingError");
            return;
        }
    Connection conn = DBConnection.getConnection();
    UserDAO userDAO = new UserDAO();

    try {
        String msg = "No changes in password.";
        String alertClass = "alert-info";

        // Check if the new password is provided
        if (newPassword == null || newPassword.trim().isEmpty()) {
            msg = "You must enter the new password.";
            alertClass = "alert-danger";
        } else {
            // Fetch the existing user and their current password
            User currentUser  = userDAO.findByUserId(userID, conn);
            String oldPassword = currentUser .getPassword();

            // 1. Check if the new password matches the old password
            if (newPassword.equals(oldPassword)) {
                msg = "The new password cannot be the same as your current password.";
                alertClass = "alert-danger";
            }
            // 2. Check if the new password is already in use by any other user
            else if (userDAO.isPasswordTaken(hashedPassword, conn)) {
                msg = "The new password is already in use by another user. Please choose a different password.";
                alertClass = "alert-danger";
            } 
            // 3. Validate the password format
            else if (!isPasswordValid(newPassword)) {
                msg = "Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one digit, and one special character (eg; Demo1006@).";
                alertClass = "alert-danger";
            } 
            else {
                // 4. Update the password
                boolean isUpdated = userDAO.updateUserPassword(userID, newPassword, conn);
                if (isUpdated) {
                    msg = "Password changed successfully.";
                    alertClass = "alert-success";
                } else {
                    msg = "Failed to change password.";
                    alertClass = "alert-danger";
                }
            }
        }

        // Pass the message and alert class back to the page
        request.setAttribute("msg", msg);
        request.setAttribute("alertClass", alertClass);
        handleEditUserRequest(request, response); // Forward back to the edit page with the message
        
         User user = new User();
            user.setPassword(hashedPassword);

            boolean userAdded = UserDAO.addUser(user, conn);
            if (userAdded) {
                response.sendRedirect("edit_user.jsp?success=UserCreated");
            } else {
                response.sendRedirect("edit_user.jsp?error=DatabaseError");
            }
    } catch (SQLException e) {
        e.printStackTrace();
        request.setAttribute("msg", "An error occurred: " + e.getMessage());
        request.setAttribute("alertClass", "alert-danger");
        handleEditUserRequest(request, response); // Forward back to the edit page with the error message
    } finally {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}    // tuutp updateUser

private boolean isPasswordValid(String password) {
    // At least 8 characters, one uppercase, one lowercase, one digit, and one special character
    String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
    return Pattern.matches(passwordPattern, password);
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



