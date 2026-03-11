<%-- 
    Document   : add_item
    Created on : Jul 2, 2024, 6:28:17 PM
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
<%@page import="com.Inventory.model.Supplier"%>
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
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Item</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
        <link rel="stylesheet" href="style.css" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
       
    </head>
    <body>
    <%@ include file="header.jsp" %>
        <div class="pageuser">
            <div class="row">
                <div class="col-md-12">
                    <c:if test="${not empty message}">
                        <div class="alert ${alertClass} alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            ${message}
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="item">
                <div class="col-md-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <strong>
                                <span class="glyphicon glyphicon-th"></span>
                                <span>Add New Item</span>
                            </strong>
                        </div>
                        <div class="panel-body">
                            <div class="col-md-12">
                                <form method="post" action="add_item" class="clearfix">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="glyphicon glyphicon-th-large"></i>
                                            </span>
                                            <input type="text" class="form-control" name="item-title" placeholder="Item Name" required>
                                        </div>
                                        <br>
                                        <div class="input-group">
                                            <span class="input-group-addon">
                                                <i class="glyphicon glyphicon-th-large"></i>
                                            </span>
                                            <input type="text" class="form-control" name="item-moq" placeholder="Item MOQ" required>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <div class="col-md-4">
                                                 <div class="input-group">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-th-large"></i>
                                                    </span>
                                                        <input type="number" class="form-control" name="item-price" placeholder="Item Price RM" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <select class="form-control" name="item-supplier" required>
                                                    <option value="">Select Item Supplier</option>
                                                    <c:forEach var="supplier" items="${all_suppliers}">
                                                        <option value="${supplier.supplierID}">${supplier.supplierName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="input-group">
                                                    <span class="input-group-addon">
                                                        <i class="glyphicon glyphicon-shopping-cart"></i>
                                                    </span>
                                                    <input type="number" class="form-control" name="item-quantity" placeholder="Item Quantity" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" name="add_item" class="btn btn-danger">Add item</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
       
        <%@ include file="footer.jsp" %>
    </body>
</html>


