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
import java.util.List;


public class SupplierServlet extends HttpServlet {
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

        if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
            return;
        }

        String message = null;
        String alertClass = "alert-info";
        List<Supplier> suppliers = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            suppliers = SupplierDAO.listAllSuppliers(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Failed to fetch suppliers";
            alertClass = "alert-danger";
        }

        request.setAttribute("suppliers", suppliers);
        request.setAttribute("message", message);
        request.setAttribute("alertClass", alertClass);
        request.getRequestDispatcher("supplier.jsp").forward(request, response);
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

        String action = request.getParameter("action");
        String message = null;
        String alertClass = "alert-info";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if ("add".equals(action)) {
                String supplierName = request.getParameter("supplier-name");
                String supplierEmail = request.getParameter("supplier-email");
                String supplierPhone = request.getParameter("supplier-phone");

                if (supplierName != null && supplierEmail != null && supplierPhone != null) {
                    Supplier newSupplier = new Supplier();
                    newSupplier.setSupplierName(supplierName);
                    newSupplier.setSupplierEmail(supplierEmail);
                    newSupplier.setSupplierPhone(supplierPhone);

                    boolean isInserted = SupplierDAO.insertSupplier(newSupplier, conn);
                    if (isInserted) {
                        message = "Successfully Added The Supplier";
                        alertClass = "alert-success";
                    } else {
                        message = "Failed to Add The Supplier";
                        alertClass = "alert-danger";
                    }
                }
            } else if ("delete".equals(action)) {
                int supplierID = Integer.parseInt(request.getParameter("supplier-id"));
                boolean isDeleted = SupplierDAO.deleteSupplier(supplierID, conn);
                if (isDeleted) {
                    message = "Successfully Deleted The Supplier";
                    alertClass = "alert-success";
                } else {
                    message = "Failed to Delete The Supplier";
                    alertClass = "alert-danger";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = "Database error occurred";
            alertClass = "alert-danger";
        }

        List<Supplier> suppliers = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            suppliers = SupplierDAO.listAllSuppliers(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("suppliers", suppliers);
        request.setAttribute("message", message);
        request.setAttribute("alertClass", alertClass);
        request.getRequestDispatcher("supplier.jsp").forward(request, response);
    }
}
