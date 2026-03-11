<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.Inventory.model.OrderItem"%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
<%@page import="com.Inventory.model.User"%>
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
    if (!PageAccessUtil.pageRequireLevel(request, response, 2)) {
        return;
    }

    String startDate = request.getParameter("start-date");
    String endDate = request.getParameter("end-date");
    List<OrderItem> orders = new ArrayList<OrderItem>();
    String alertMessage = "";

    if (startDate != null && endDate != null) {
        // Database connection details
        String url = "jdbc:mysql://interchange.proxy.rlwy.net:33380/railway";
        String dbUser = "root";
        String password = "BCptazPhAsXTGhZpcFQuRtWyUjWsujjN";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establish connection
            conn = DriverManager.getConnection(url, dbUser, password);
            
            String sql = "SELECT oi.orderitemID, oi.orderID, oi.itemID, oi.order_qty, oi.orderStatus, o.orderDate, i.itemName, i.itemPrice, s.supplierName, u.name " +
                         "FROM order_item oi " +
                         "JOIN orders o ON oi.orderID = o.orderID " +
                         "JOIN items i ON oi.itemID = i.itemID " +
                         "JOIN suppliers s ON i.supplierID = s.supplierID " +
                         "JOIN users u ON o.userID = u.userID " +
                         "WHERE o.orderDate BETWEEN ? AND ?" +
                         "ORDER BY DATE(o.orderDate) DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            rs = stmt.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderitemId(rs.getInt("orderitemID"));
                orderItem.setOrderId(rs.getInt("orderID"));
                orderItem.setItemId(rs.getInt("itemID"));
                orderItem.setOrderQty(rs.getInt("order_qty"));
                orderItem.setOrderStatus(rs.getString("orderStatus"));
                orderItem.setOrderDate(rs.getDate("orderDate"));
                orderItem.setItemName(rs.getString("itemName"));
                orderItem.setItemPrice(rs.getInt("itemPrice"));
                orderItem.setSupplierName(rs.getString("supplierName"));
                orderItem.setUserName(rs.getString("name"));
                orders.add(orderItem);
            }

            // Check if orders were found
            if (orders.isEmpty()) {
                alertMessage = "No orders found for the given date range.";
                response.sendRedirect("orders_report.jsp?alertMessage=" + java.net.URLEncoder.encode(alertMessage, "UTF-8"));
                return;
            }

            // Set attributes for JSTL
            request.setAttribute("orders", orders);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

        } catch (SQLException e) {
            e.printStackTrace();
            alertMessage = "An error occurred: " + e.getMessage();
            response.sendRedirect("order_report.jsp?alertMessage=" + java.net.URLEncoder.encode(alertMessage, "UTF-8"));
            return;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    } else {
        alertMessage = "Please select a date range.";
        response.sendRedirect("order_report.jsp?alertMessage=" + java.net.URLEncoder.encode(alertMessage, "UTF-8"));
        return;
    }
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Orders Report</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <style>
        @media print {
            html, body {
                font-size: 9.5pt;
                margin: 0;
                padding: 0;
            }
            .page-break {
                page-break-before: always;
                width: auto;
                margin: auto;
            }
        }
        .page-break {
            width: 1090px;
            margin: 0 auto;
        }
        .order-head {
            margin: 40px 0;
            text-align: center;
        }
        .order-head h1, .order-head strong {
            padding: 10px 20px;
            display: block;
        }
        .order-head h1 {
            margin: 0;
            border-bottom: 1px solid #212121;
        }
        .table>thead:first-child>tr:first-child>th {
            border-top: 1px solid #000;
        }
        table thead tr th {
            text-align: center;
            border: 1px solid #ededed;
        }
        table tbody tr td {
            vertical-align: middle;
        }
        .order-head, table.table thead tr th, table tbody tr td, table tfoot tr td {
            border: 1px solid #212121;
            white-space: nowrap;
        }
        .order-head h1, table thead tr th, table tfoot tr td {
            background-color: #f8f8f8;
        }
        tfoot {
            color: #000;
            text-transform: uppercase;
            font-weight: 500;
        }
        .text-center{
            text-align: center;
        }
    </style>
</head>
<body>
    <% if (orders.size() > 0) { %>
        <div class="page-break">
            <div class="order-head">
                <h1>Inventory Management System - Orders Report</h1>
                <strong><%= startDate %> TILL DATE <%= endDate %></strong>
            </div>
            <table class="table table-border">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Order Date</th> 
                        <th>Item Name</th> 
                        <th>Supplier</th> 
                        <th>Order Status</th>
                        <th>Order Quantity</th>
                        <th>Item Price (RM)</th>
                        <th>Total (RM)</th>
                        <th>Placed By</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td><c:out value="OR00${order.orderId}"/></td>
                            <td><c:out value="${order.orderDate}"/></td>
                            <td><c:out value="${order.itemName}"/></td>
                            <td><c:out value="${order.supplierName}"/></td> 
                            <td class="text-center"><c:out value="${order.orderStatus}"/></td>
                            <td class="text-center"><c:out value="${order.orderQty}"/></td>                            
                            <td class="text-center"><c:out value="${order.itemPrice}"/></td>
                            <td class="text-center"><c:out value="${order.orderQty * order.itemPrice}"/></td>
                             <td><c:out value="${order.userName}"/></td>
                           
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr class="text-center">
                        <td colspan="4"></td>
                        <td colspan="1">Total Order Quantity</td>
                        <td colspan="1">
                            <%
                                int totalOrderQty = 0; // Initialize totalOrderQty
                                for (OrderItem order : orders) {
                                    if ("Complete".equalsIgnoreCase(order.getOrderStatus())) {
                                         totalOrderQty += order.getOrderQty(); // Calculate total quantity
                                    }
                                }
                                out.print(totalOrderQty); // Print the total quantity
                            %>
                        </td>
                        <br>    
                        <td colspan="1">Total Order Price (RM)</td>
                        <td colspan="1">
                            <%
                                double totalOrderPrice = 0.0; // Initialize totalOrderPrice as a double
                                for (OrderItem order : orders) {
                                    if ("Complete".equalsIgnoreCase(order.getOrderStatus())) {
                                        totalOrderPrice += order.getOrderQty() * order.getItemPrice(); // Calculate total price
                                    }
                                }
                                out.print(totalOrderPrice); // 
                            %>
                        </td>
                    </tr>
                </tfoot>

            </table>
            <div class="text-center">
                <a href="orders_report.jsp" class="btn btn-primary">DONE</a>
            </div>
        </div>
    <% } else { %>
        <div class="alert alert-danger">
            Sorry, no orders have been found.
        </div>
    <% } %>
</body>
</html>
