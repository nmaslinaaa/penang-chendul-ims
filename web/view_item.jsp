<%-- 
    Document   : view_item
    Created on : Jul 2, 2024, 6:33:33 PM
    Author     : User
--%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.Inventory.DAO.ItemDAO"%>
<%@page import="com.Inventory.model.Item"%>
<%@page import="com.Inventory.model.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

    // Initialize message variable
    String message = (String) request.getAttribute("message");
    if (message == null) message = "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Item</title>
    
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <style>
        /* Modal styles */
        .modal-dialog-centered {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .modal-content {
            text-align: center;
            padding: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="pageuser">
        <div class="viewitem">
            <div class="col-md-12">
                <c:if test="${not empty message}">
                    <div class="alert alert-info" role="alert">
                        ${message}
                    </div>
                </c:if>
            </div>

            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading clearfix">
                        <strong>
                            <span class="glyphicon glyphicon-th"></span>
                            <span>Items List</span>
                        </strong>
                    </div>
                    <div class="panel-body">
                        <c:if test="${empty items}">
                            <!-- Modal for no items -->
                            <div class="modal fade" id="noItemModal" tabindex="-1" role="dialog" aria-hidden="true">
                                <div class="modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <h4>No items available in the store.</h4>
                                        <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
                                    </div>
                                </div>
                            </div>
                            <script>
                                $(document).ready(function () {
                                    $("#noItemModal").modal('show');
                                });
                            </script>
                        </c:if>

                        <c:if test="${not empty items}">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th class="text-center" style="width: 5%;">Item ID</th>
                                        <th style="width: 10%;">Item Name</th>
                                        <th style="width: 10%;">Item MOQ</th>
                                        <th class="text-center" style="width: 10%;">Item Price (RM)</th>
                                        <th class="text-center" style="width: 10%;">Suppliers</th>
                                        <th class="text-center" style="width: 10%;">Current Quantity</th>
                                        <th class="text-center" style="width: 10%;">Item Added</th>
                                        <th class="text-center" style="width: 10%;">Added By</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${items}">
                                        <tr>
                                            <td class="text-center">IT00${item.itemID}</td>
                                            <td>${item.itemName}</td>
                                            <td>${item.MOQ}</td>
                                            <td class="text-center">RM ${item.itemPrice}</td>
                                            <td class="text-center">${item.supplierName}</td>
                                            <td class="text-center">${item.currentQty}</td>
                                            <td class="text-center">
                                                <fmt:formatDate value="${item.dateAdded}" pattern="yyyy-MM-dd" />
                                            </td>
                                            <td class="text-center">${item.username}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
