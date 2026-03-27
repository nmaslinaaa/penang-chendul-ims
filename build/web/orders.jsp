<%-- 
    Document   : orders
    Created on : Jul 13, 2024
    Author     : User
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.Inventory.DAO.OrderDAO"%>
<%@page import="com.Inventory.DAO.OrderItemDAO"%>
<%@page import="com.Inventory.DAO.ItemDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.Inventory.model.OrderItem"%>
<%@page import="com.Inventory.model.User"%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.Inventory.utill.SessionUtil"%>

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

    String message = (String) session.getAttribute("message");
    String alertClass = (String) session.getAttribute("alertClass");

    if (message != null && alertClass != null) {
        session.removeAttribute("message");
        session.removeAttribute("alertClass");
    }

    List<OrderItem> orderItemsList = null;

    String url = "jdbc:mysql://crossover.proxy.rlwy.net:18818/railway?useSSL=true&requireSSL=true&verifyServerCertificate=false";
    String dbUser = "root";
    String password = "qNlcSowNDJcrRXRHZHdokKkdpabmZipu";
    Connection connection = null;

    try {
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection(url, dbUser, password);

        // Load all order items
        orderItemsList = OrderItemDAO.listAllOrderItems(connection);
    } catch (Exception e) {
        e.printStackTrace();
        message = "An error occurred: " + e.getMessage();
        alertClass = "alert-danger";
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    request.setAttribute("message", message);
    request.setAttribute("alertClass", alertClass);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>All Orders</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="style.css" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </head>
    <body>
        <%@ include file="header.jsp" %>
        <div class="pageuser">
            <div class="row">
                <div class="col-md-6">
                    <div id="alert-container">
                        <c:if test="${not empty message}">
                            <div class="alert ${alertClass} alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                ${message}
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
                                <span>All Orders</span>
                            </strong>
                            <div class="pull-right">
                                <a href="add_order" class="btn btn-primary">Add Order</a>
                            </div>
                        </div>
                        <div class="panel-body">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="text-center" style="width: 7%;">Order ID</th>
                                        <th class="text-center" style="width: 7%;">#</th>
                                        <th>Item Name</th>
                                        <th class="text-center" style="width: 15%;">Quantity</th>
                                        <th class="text-center" style="width: 15%;">Date</th>
                                        <th class="text-center" style="width: 15%;">Status</th>
                                        <th class="text-center" style="width: 80px;">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        String currentOrderID = "";
                                        int itemCount = 0;
                                        for (OrderItem orderItem : orderItemsList) {
                                            if (!currentOrderID.equals(String.valueOf(orderItem.getOrderId()))) {
                                                currentOrderID = String.valueOf(orderItem.getOrderId());
                                                itemCount = 1; // Reset item count for new order
                                            } else {
                                                itemCount++; // Increment item count for the same order
                                            }
                                    %>
                                    <tr>
                                        <td class="text-center"><%= (itemCount == 1) ? "OR00" + orderItem.getOrderId() : "" %></td>
                                        <td class="text-center"><%= itemCount %></td>
                                        <td><%= orderItem.getItemName() %></td>
                                        <td class="text-center"><%= orderItem.getOrderQty() %></td>
                                        <td class="text-center"><%= orderItem.getOrderDate() %></td>
                                        <td class="text-center">
                                            <form method="post" class="status-form">
                                                <input type="hidden" name="orderitemID" value="<%= orderItem.getOrderitemId() %>">
                                                <select name="status" class="form-control" onchange="submitStatusForm(this)">
                                                    <option value="Incomplete" <%= "Incomplete".equals(orderItem.getOrderStatus()) ? "selected" : "" %>>Incomplete</option>
                                                    <option value="Complete" <%= "Complete".equals(orderItem.getOrderStatus()) ? "selected" : "" %>>Complete</option>
                                                </select>
                                                <input type="hidden" name="update_status" value="1">
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <button type="button" class="btn btn-danger btn-xs" title="Delete" data-toggle="tooltip" onclick="confirmDelete(<%= orderItem.getOrderitemId() %>)">
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button>
                                        </td>
                                    </tr>
                                    <% } %>
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
                        Are you sure you want to delete this order item? You will lose all the details associated with this item.
                    </div>
                    <div class="modal-footer">
                        <form method="post" action="deleteOrderItem">
                            <input type="hidden" name="orderitemID" id="orderitemID" value="">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-danger">Yes</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function submitStatusForm(selectElement) {
                var form = selectElement.form;
                $.ajax({
                    type: form.method,
                    url: 'updateOrderStatus',
                    data: $(form).serialize(),
                    success: function(response) {
                        var alertMessage = response.message;
                        var alertClass = response.alertClass;
                        $('#alert-container').html('<div class="alert ' + alertClass + ' alert-dismissible" role="alert">' +
                            '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                            alertMessage + '</div>');
                    },
                    error: function() {
                        $('#alert-container').html('<div class="alert alert-danger alert-dismissible" role="alert">' +
                            '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                            'Failed to update status. Please try again.</div>');
                    }
                });
            }

            function confirmDelete(orderitemID) {
                $('#orderitemID').val(orderitemID);
                $('#deleteModal').modal('show');
            }
        </script>
        <%@ include file="footer.jsp" %>
    </body>
</html>

