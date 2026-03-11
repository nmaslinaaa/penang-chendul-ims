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
    if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
        return;
    }

    // Database connection details
    String url = "jdbc:mariadb://localhost:3306/pcims";
    String dbUser = "root";
    String password = "";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add New User</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="pageadduser">
        <div class="container">
            <div class="row">
                <%
                    String error = request.getParameter("error");
                    String success = request.getParameter("success");
                    if (error != null) {
                %>
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <%= error %>
                    </div>
                <% } else if (success != null) { %>
                    <div class="alert alert-success alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        User account has been created!
                    </div>
                <% } %>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <strong>
                            <span class="glyphicon glyphicon-th"></span>
                            <span>Add New User</span>
                        </strong>
                    </div>
                    <div class="panel-body">
                        <div class="col-md-6">
                        <form method="post" action="AddUserServlet">
                            <div class="form-group">
                                <label for="name">Full Name</label>
                                <input type="text" class="form-control" name="full-name" placeholder="Full Name" required>
                            </div>
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" class="form-control" name="username" placeholder="Username" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control" name="password" placeholder="Password" required>
                            </div>
                            <div class="form-group">
                                <label for="level">User Role</label>
                                <select class="form-control" name="level" required>
                                    <option value="">Select Role</option>
                                    <option value="1">Manager</option>
                                    <option value="2">Supervisor</option>
                                </select>
                            </div>
                            <div class="form-group clearfix">
                                <button type="submit" name="add_user" class="btn btn-primary">Add User</button>
                                <button type="button" name="cancel" onclick="window.history.back();" class="btn btn-danger">Cancel</button>
                            </div>
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
