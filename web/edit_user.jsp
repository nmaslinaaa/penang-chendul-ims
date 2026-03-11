<%-- 
    Document   : edit_user
    Created on : Jul 2, 2024, 6:28:17 PM
    Author     : User
--%>
<%@page import="com.Inventory.model.DBConnection"%>
<%@page import="com.Inventory.DAO.UserDAO"%>
<%@page import="com.Inventory.model.User"%>
<%@page import="com.Inventory.model.UserGroup"%>
<%@page import="com.Inventory.utill.SessionUtil"%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    User user = SessionUtil.getCurrentUser(request);
    if (user == null) {
        SessionUtil.setErrorMessage(request, "Please login...");
        response.sendRedirect("login.jsp");
        return;
    }

    if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
        return;
    }

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
    } catch (SQLException e) {
        e.printStackTrace();
    }

    if (e_user == null) {
        response.sendRedirect("users.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit User</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
   
</head>
<body>
<%@ include file="header.jsp" %>
<div class="pageuser">
    <div class="row">
        <div class="col-md-12">
            <c:if test="${not empty msg}">
                <div class="alert ${alertClass} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    ${msg}
                </div>
            </c:if>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <strong>
                        <span class="glyphicon glyphicon-th"></span>
                        Update ${e_user.name} Account
                    </strong>
                </div>
                <div class="panel-body">
                    <form method="post" action="UserController?action=update" class="clearfix">
                        <input type="hidden" name="userID" value="${e_user.userID}">
                        <div class="form-group">
                            <label for="name" class="control-label">Name</label>
                            <input type="text" class="form-control" name="name" value="${e_user.name}">
                        </div>
                        <div class="form-group">
                            <label for="username" class="control-label">Username</label>
                            <input type="text" class="form-control" name="username" value="${e_user.username}">
                        </div>
                        <div class="form-group">
                            <label for="level">User Role</label>
                            <select class="form-control" name="level">
                                <c:forEach var="group" items="${groups}">
                                    <option value="${group.groupLevel}" <c:if test="${group.groupLevel == e_user.user_level}">selected</c:if>>${group.groupName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="status">Status</label>
                            <select class="form-control" name="status">
                                <option value="1" <c:if test="${e_user.status == 1}">selected</c:if>>Active</option>
                                <option value="0" <c:if test="${e_user.status == 0}">selected</c:if>>Deactive</option>
                            </select>
                        </div>
                        <div class="form-group clearfix">
                            <button type="submit" name="update" class="btn btn-info">Update</button>
                            <button type="button" class="btn btn-danger" onclick="window.location.href='users.jsp'">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- Change password form -->
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <strong>
                        <span class="glyphicon glyphicon-th"></span>
                        Change ${e_user.name} password
                    </strong>
                </div>
                <div class="panel-body">
                    <form action="UserController?action=update-pass" method="post" class="clearfix">
                        <input type="hidden" name="userID" value="${e_user.userID}">
                        <div class="form-group">
                            <label for="newPassword" class="control-label" >New password</label>
                            <input type="password"  class="form-control" value="${e_user.password}" name="newPassword" placeholder="Enter New password">
                      </div>
                        <div class="form-group clearfix">
                            <button type="submit" name="update-pass" class="btn btn-danger pull-right">Change</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>







