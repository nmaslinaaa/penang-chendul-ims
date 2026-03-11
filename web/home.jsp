<%@page import="java.sql.SQLException"%>
<%@page import="com.Inventory.DAO.ItemDAO"%>
<%@page import="com.Inventory.DAO.OrderDAO"%>
<%@page import="com.Inventory.DAO.OrderItemDAO"%>
<%@page import="com.Inventory.DAO.UserDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.Inventory.model.Item"%>
<%@page import="com.Inventory.model.OrderItem"%>
<%@page import="com.Inventory.DAO.SupplierDAO"%>
<%@page import="com.Inventory.model.User"%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.Inventory.model.User"%>
<%@ page import="com.Inventory.utill.SessionUtil"%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.

    User user = SessionUtil.getCurrentUser(request);
    if (user == null) {
        SessionUtil.setErrorMessage(request, "Please login...");
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%
    // Check user level and redirect if not authorized
    if (!PageAccessUtil.pageRequireLevel(request, response, 2)) {
        return;
    }

    // Database connection details
    String url = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
    String dbUser = "root";
    String password = "BCptazPhAsXTGhZpcFQuRtWyUjWsujjN";

    // Get alert message from query parameters
    String msg = request.getParameter("msg");
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inventory Management System</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
    <style>
        .alert-info {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <%@include file="header.jsp" %>
    <div class="pagehome">
        <div class="row">
            <div class="col-md-12">
                <% if (msg != null && !msg.isEmpty()) { %>
                    <div class="alert alert-info">
                        <%= msg %>
                    </div>
                <% } %>
            </div>
            <div class="col-md-12">
                <div class="panel">
                    <div class="jumbotron text-center">
                        <h1>Welcome User <hr> Inventory Management System</h1>
                        <p>Browse around to find out the pages that you can access!</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>
