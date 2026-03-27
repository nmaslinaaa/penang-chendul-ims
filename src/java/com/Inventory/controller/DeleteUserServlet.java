
package com.Inventory.controller;

import com.Inventory.DAO.UserDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");

        if (userID == null || userID.isEmpty()) {
            response.sendRedirect("users.jsp");
            return;
        }

        // Database connection details
        String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
        String dbUser = "root";
        String password = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";

        Connection conn = null;

        try {
            // Establish connection
            conn = DriverManager.getConnection(url, dbUser, password);

            // Delete user
            UserDAO.deleteUser(conn, Integer.parseInt(userID));

            // Set success message
            request.getSession().setAttribute("msg", "User successfully deleted");

        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Unable to delete user");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Redirect back to users list
        response.sendRedirect("users.jsp");
    }
}


