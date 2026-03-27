/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.controller;

import com.Inventory.DAO.SupplierDAO;
import com.Inventory.model.Supplier;
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


public class EditSupplierServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
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

        if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int supplierID = Integer.parseInt(request.getParameter("id"));
            Supplier supplier = SupplierDAO.getSupplier(supplierID, conn);
            request.setAttribute("supplier", supplier);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/edit_supplier.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = SessionUtil.getCurrentUser(request);
        if (user == null) {
            SessionUtil.setErrorMessage(request, "Please login...");
            response.sendRedirect("login.jsp");
            return;
        }

        if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
            return;
        }

        String message = null;
        String alertClass = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int supplierID = Integer.parseInt(request.getParameter("id"));
            Supplier supplier = SupplierDAO.getSupplier(supplierID, conn);

            String supplierName = request.getParameter("supplier-name");
            String supplierEmail = request.getParameter("supplier-email");
            String supplierPhone = request.getParameter("supplier-phone");

            if (supplierName != null && supplierEmail != null && supplierPhone != null) {
                if (supplierName.equals(supplier.getSupplierName()) &&
                        supplierEmail.equals(supplier.getSupplierEmail()) &&
                        supplierPhone.equals(supplier.getSupplierPhone())) {
                    message = "No changes were made.";
                    alertClass = "alert-info";
                } else {
                    Supplier updatedSupplier = new Supplier(supplierID, supplierName, supplierEmail, supplierPhone);
                    boolean isUpdated = SupplierDAO.updateSupplier(updatedSupplier, conn);
                    if (isUpdated) {
                        message = "Successfully updated the supplier.";
                        alertClass = "alert-success";
                        supplier = updatedSupplier; // Update the current supplier with new values
                    } else {
                        message = "Failed to update the supplier.";
                        alertClass = "alert-danger";
                    }
                }
            }

            request.setAttribute("supplier", supplier);
            request.setAttribute("message", message);
            request.setAttribute("alertClass", alertClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/edit_supplier.jsp").forward(request, response);
    }
}
