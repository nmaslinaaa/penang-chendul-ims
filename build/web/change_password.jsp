<%-- 
    Document   : add_item
    Created on : Jul 2, 2024, 6:28:17 PM
    Author     : User
--%>
<%@page import="com.Inventory.model.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.Inventory.utill.SessionUtil"%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.

    User currentUser = SessionUtil.getCurrentUser(request);
    if (currentUser == null) {
        SessionUtil.setErrorMessage(request, "Please login...");
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <%@include file = "header.jsp" %>
        <div class="pageuser">
            <div class="login-page">
                <div class="text-center">
                  <h3>Change your password</h3>
                </div>
                <c:if test="${not empty message}">
                    <div class="alert ${alertClass}" role="alert">
                        ${message}
                    </div>
                </c:if>
                <form method="post" action="change_password" class="clearfix">
                  <div class="form-group">
                    <label for="newPassword" class="control-label">New password</label>
                    <input type="password" class="form-control" name="new-password" placeholder="New password">
                  </div>
                  <div class="form-group">
                    <label for="oldPassword" class="control-label">Old password</label>
                    <input type="password" class="form-control" name="old-password" placeholder="Old password">
                  </div>
                  <div class="form-group clearfix">
                    <input type="hidden" name="userID" value="<%= currentUser.getUserID() %>">
                    <button type="submit" name="update" class="btn btn-info">Change</button>
                  </div>
                </form>
              </div>
        </div>
        <%@ include file = "footer.jsp" %>
    </body>
</html>
