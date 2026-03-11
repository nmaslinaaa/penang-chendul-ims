<%-- 
    Document   : users
    Created on : Jul 2, 2024, 12:48:35 AM
    Author     : User
--%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
<%@page import="com.Inventory.utill.SessionUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="com.Inventory.model.User"%>
<%@ page import="com.Inventory.DAO.UserDAO"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.SQLException"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="header.jsp" %>

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

    // Check user level and redirect if not authorized
    if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
        return;
    }

    // Database connection details
    String url = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
    String dbUser = "root";
    String password = "BCptazPhAsXTGhZpcFQuRtWyUjWsujjN";

    Connection conn = null;
    List<User> all_users = null;

    try {
        // Establish connection
        conn = DriverManager.getConnection(url, dbUser, password);

        // Fetch user data
        all_users = UserDAO.findAllUsers(conn);

        // Set attributes for JSTL
        request.setAttribute("all_users", all_users);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Transfer session attributes to request attributes
    String msg = (String) session.getAttribute("msg");
    if (msg != null) {
        request.setAttribute("msg", msg);
        session.removeAttribute("msg");
    }

    String error = (String) session.getAttribute("error");
    if (error != null) {
        request.setAttribute("error", error);
        session.removeAttribute("error");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Users Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="pageuser">
        <div class="rowuser">
            <div class="col-md-12">
                <div id="alert-container">
                    <c:if test="${not empty msg}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            ${msg}
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            ${error}
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading clearfix">
                        <strong>
                            <span class="glyphicon glyphicon-th"></span>
                            <span>Users</span>
                        </strong>
                        <a href="add_user.jsp" class="btn btn-info pull-right">Add New User</a>
                    </div>
                    <div class="panel-body">
                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th class="text-center" style="width: 50px;">#</th>
                                    <th>Name</th>
                                    <th>Username</th>
                                    <th class="text-center" style="width: 15%;">User Role</th>
                                    <th class="text-center" style="width: 10%;">Status</th>
                                    <th style="width: 20%;">Last Login</th>
                                    <th class="text-center" style="width: 100px;">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${all_users}">
                                    <tr>
                                        <td class="text-center"><c:out value="${all_users.indexOf(user) + 1}"/></td>
                                        <td><c:out value="${user.name}"/></td>
                                        <td><c:out value="${user.username}"/></td>
                                        <td class="text-center"><c:out value="${user.group_name}"/></td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${user.status == 1}">
                                                    <span class="label label-success">Active</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="label label-danger">Inactive</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${user.last_login}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td class="text-center">
                                            <div class="btn-group">
                                                <a href="UserController?userID=<c:out value='${user.userID}'/>" class="btn btn-xs btn-warning" data-toggle="tooltip" title="Edit">
                                                    <i class="glyphicon glyphicon-pencil"></i>
                                                </a>
                                                <button type="button" class="btn btn-xs btn-danger" data-toggle="tooltip" title="Remove" onclick="confirmDelete('${user.userID}')">
                                                    <i class="glyphicon glyphicon-remove"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="deleteModalLabel">Confirm Delete</h4>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete this user? You will lose all the details associated with this user.
                </div>
                <div class="modal-footer">
                    <form method="get" action="DeleteUserServlet">
                        <input type="hidden" name="userID" id="userID" value="">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-default">Yes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function confirmDelete(userID) {
            $('#userID').val(userID);
            $('#deleteModal').modal('show');
        }

        window.onload = function() {
            setTimeout(function() {
                var alert = document.querySelector('.alert');
                if (alert) {
                    alert.style.display = 'none';
                }
            }, 5000); // Hide the alert after 1.5 seconds
        };
    </script>
    <%@ include file="footer.jsp" %>
</body>
</html>








