/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.utill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.Inventory.model.User;

public class PageAccessUtil {

    public static boolean pageRequireLevel(HttpServletRequest request, HttpServletResponse response, int requiredLevel) throws IOException {
        User currentUser = (User) request.getSession().getAttribute("user");

        // If user not logged in
        if (currentUser == null) {
            response.sendRedirect("index.jsp?msg=" + java.net.URLEncoder.encode("Please login...", "UTF-8"));
            return false;
        }

        // If user status is deactivated
        if (currentUser.getStatus() == 0) {
            response.sendRedirect("home.jsp?msg=" + java.net.URLEncoder.encode("Your account has been deactivated!", "UTF-8"));
            return false;
        }

        // Checking logged-in user's level against the required level
        if (currentUser.getUser_level() <= requiredLevel) { // Changed to >= to match required level
            return true;
        } else {
            response.sendRedirect("home.jsp?msg=" + java.net.URLEncoder.encode("Sorry! You don't have permission to view the page.", "UTF-8"));
            return false;
        }
    }
}


