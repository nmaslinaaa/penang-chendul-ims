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
<%@ page import="javax.servlet.http.HttpSession"%>

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
    if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
        return;
    }

    // Database connection details
    String url = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
    String dbUser = "root";
    String password = "BCptazPhAsXTGhZpcFQuRtWyUjWsujjN";

    Connection conn = null;
    int c_supplier = 0;
    int c_item = 0;
    int c_order = 0;
    int c_user = 0;
    List<Item> recent_items = null;
    List<OrderItem> recent_orders = null;

    try {
        // Establish connection
        conn = DriverManager.getConnection(url, dbUser, password);
        
        // Fetch data
        c_supplier = SupplierDAO.countBySupplierId(conn);
        c_item = ItemDAO.countByItemId(conn);
        c_order = OrderDAO.countByOrderId(conn);
        c_user = UserDAO.countByUserId(conn);
        recent_items = ItemDAO.findRecentItemAdded(50, conn);
        recent_orders = OrderItemDAO.findRecentOrderAdded(10, conn);

        // Set attributes for JSTL
        request.setAttribute("recent_items", recent_items);
        request.setAttribute("recent_orders", recent_orders);
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
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Home Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="page">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                </div>
            </div>
            <div class="row">
                <a href="users.jsp" style="color:black;">
                    <div class="col-md-3">
                        <div class="panel panel-box clearfix">
                            <div class="panel-icon pull-left bg-secondary1">
                                <i class="glyphicon glyphicon-user"></i>
                            </div>
                            <div class="panel-value pull-right">
                                <h2 class="margin-top"><%= c_user %></h2>
                                <p class="text-muted">Users</p>
                            </div>
                        </div>
                    </div>
                </a>

                <a href="supplier" style="color:black;">
                    <div class="col-md-3">
                        <div class="panel panel-box clearfix">
                            <div class="panel-icon pull-left bg-red">
                                <i class="glyphicon glyphicon-briefcase"></i>
                            </div>
                            <div class="panel-value pull-right">
                                <h2 class="margin-top"><%= c_supplier %></h2>
                                <p class="text-muted">Suppliers</p>
                            </div>
                        </div>
                    </div>
                </a>

                <a href="item" style="color:black;">
                    <div class="col-md-3">
                        <div class="panel panel-box clearfix">
                            <div class="panel-icon pull-left bg-blue2">
                                <i class="glyphicon glyphicon-shopping-cart"></i>
                            </div>
                            <div class="panel-value pull-right">
                                <h2 class="margin-top"><%= c_item %></h2>
                                <p class="text-muted">Items</p>
                            </div>
                        </div>
                    </div>
                </a>

                <a href="orders.jsp" style="color:black;">
                    <div class="col-md-3">
                        <div class="panel panel-box clearfix">
                            <div class="panel-icon pull-left bg-green">
                                <i class="glyphicon glyphicon-tags"></i>
                            </div>
                            <div class="panel-value pull-right">
                                <h2 class="margin-top"><%= c_order %></h2>
                                <p class="text-muted">Orders</p>
                            </div>
                        </div>
                    </div>
                </a>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <strong>
                                <span class="glyphicon glyphicon-th"></span>
                                <span>RECENTLY ORDER-ITEM</span>
                            </strong>
                        </div>
                        <div class="panel-body">
                            <table class="table table-striped table-bordered table-condensed">
                                <thead>
                                    <tr>
                                        <th class="text-center" style="width: 50px;">#</th>
                                        <th>Item Name</th>
                                        <th>Date</th>
                                        <th class="text-center">Total Order</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="recent_order" items="${recent_orders}">
                                        <tr>
                                            <td class="text-center"><c:out value="${recent_orders.indexOf(recent_order) + 1}"/></td>
                                            <td>
                                                <a href="orders.jsp?id=<c:out value="${recent_order.orderitemId}"/>">
                                                    <c:out value="${recent_order.itemName}"/>
                                                </a>
                                            </td>
                                            <td><c:out value="${recent_order.orderDate}"/></td>
                                            <td class="text-center"><c:out value="${recent_order.orderQty}"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <strong>
                                <span class="glyphicon glyphicon-th"></span>
                                <span>Current Quantity</span>
                            </strong>
                        </div>
                        <div class="panel-body">
                            <div class="list-group">
                                <c:forEach var="recent_item" items="${recent_items}">
                                    <a class="list-group-item clearfix" href="edit_item?id=<c:out value="${recent_item.itemID}"/>">
                                        <h4 class="list-group-item-heading">
                                            <c:out value="${recent_item.itemName}"/>
                                            <span class="label label-warning pull-right">
                                                <c:out value="${recent_item.currentQty}"/>
                                            </span>
                                        </h4>
                                        <span class="list-group-item-text pull-right">
                                            <c:out value="${recent_item.supplierName}"/>
                                        </span>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                
<!--                <div class="col-md-5">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <strong>
                                <span class="glyphicon glyphicon-th"></span>
                                <span>Current Quantity</span>
                            </strong>
                        </div>
                        <div class="panel-body">
                            <div class="list-group">
                                <c:forEach var="recent_item" items="${recent_items}">
                                    <a class="list-group-item clearfix" href="edit_item?id=<c:out value="${recent_item.itemID}"/>">
                                        <h4 class="list-group-item-heading">
                                            <c:out value="${recent_item.itemName}"/>
                                            <span class="label label-warning pull-right">
                                                <c:out value="${recent_item.currentQty}"/>
                                            </span>
                                        </h4>
                                        <span class="list-group-item-text pull-right">
                                            <c:out value="${recent_item.supplierName}"/>
                                        </span>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>-->
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
