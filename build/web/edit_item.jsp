<%-- 
    Document   : edit_supplier
    Created on : Jul 2, 2024, 6:28:17 PM
    Author     : User
--%>
<%-- 
    Document   : edit_supplier
    Created on : Jul 2, 2024, 6:28:17 PM
    Author     : User
--%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="com.Inventory.model.Item"%>
<%@ page import="com.Inventory.DAO.SupplierDAO"%>
<%@ page import="com.Inventory.model.User"%>
<%@ page import="com.Inventory.utill.PageAccessUtil"%>
<%@ page import="com.Inventory.model.Supplier"%>
<%@ page import="com.Inventory.utill.SessionUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <title>Edit Item</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div class="pagesupplier">
            <div class="edititem">
                <div class="row">
                    <div class="col-md-12">
                        <c:if test="${not empty message}">
                            <div class="alert ${alertType}" role="alert">
                                ${message}
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <strong>
                            <span class="glyphicon glyphicon-th"></span>
                            <span>Edit Item ${item.itemName}</span>
                        </strong>
                    </div>
                    <div class="panel-body">
                        <div class="col-md-7">
                            <form method="post" action="edit_item?id=${item.itemID}">
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="glyphicon glyphicon-th-large"></i>
                                        </span>
                                        <input type="text" class="form-control" name="item-title" value="${item.itemName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="glyphicon glyphicon-th-large"></i>
                                        </span>
                                        <input type="text" class="form-control" name="item-moq" value="${item.MOQ}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="glyphicon glyphicon-th-large"></i>
                                        </span>
                                        <input type="text" class="form-control" name="item-price" value="${item.itemPrice}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <select class="form-control" name="item-supplier">
                                                <option value="">Select a supplier</option>
                                                <c:forEach var="supplier" items="${all_suppliers}">
                                                    <option value="${supplier.supplierID}" <c:if test="${item.supplierID == supplier.supplierID}">selected</c:if>>${supplier.supplierName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                    <div class="edititem">   
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label for="qty">Quantity</label>
                                                        <div class="input-group">
                                                            <span class="input-group-addon">
                                                                <i class="glyphicon glyphicon-shopping-cart"></i>
                                                            </span>
                                                            <input type="number" class="form-control" name="item-quantity" value="${item.currentQty}">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="submit" name="item" class="btn btn-danger">Update</button>
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

