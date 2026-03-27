<%-- 
    Document   : order_report
    Created on : Jul 2, 2024, 6:33:33 PM
    Author     : User
--%>
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
    if (!PageAccessUtil.pageRequireLevel(request, response, 3)) {
        return;
    }

    // Database connection details
    String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=false&allowPublicKeyRetrieval=true";
    String dbUser = "root";
    String password = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";

    // Get alert message from query parameters
    String alertMessage = request.getParameter("alertMessage");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Order Report</title>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
     <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
     <link rel="stylesheet" href="style.css" />
    <style>
        .alert-info{
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
        
        .alert{
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            margin-left: -7%;
            margin-right: -14%;
        }
    </style>
</head>
<body>
    <%@include file="header.jsp" %>
    <div class="pageuser">
        <div class="row">
            <div class="col-md-6">
                <div class="col-md-6">
                    <% if (alertMessage != null && !alertMessage.isEmpty()) { %>
                        <div class="alert alert-info">
                            <%= alertMessage %>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="panel">
                    <div class="panel-heading">
                        <strong>
                            <span class="glyphicon glyphicon-th"></span>
                            <span>Generate Order Report</span>
                        </strong>
                    </div>
                    <div class="panel-body">
                        <form class="clearfix" method="post" action="order_report_process.jsp">
                            <div class="form-group">
                                <label class="form-label">Date Range</label>
                                <div class="input-group">
                                    <input type="text" class="datepicker form-control" name="start-date" placeholder="From">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-menu-right"></i></span>
                                    <input type="text" class="datepicker form-control" name="end-date" placeholder="To">
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="submit" name="submit" class="btn btn-primary">Generate Report</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.datepicker').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayHighlight: true,
                defaultViewDate: { year: new Date().getFullYear(), month: new Date().getMonth(), day: new Date().getDate() }
            }).datepicker('update', new Date());
        });
    </script> 
    
    <%@include file="footer.jsp" %>
</body>
</html>
