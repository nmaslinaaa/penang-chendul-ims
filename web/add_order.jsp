<%-- 
    Document   : add_order
    Created on : Jul 2, 2024, 6:28:17 PM
    Author     : User
--%>

<%@page import="java.util.List"%>
<%@page import="com.Inventory.model.Item"%>
<%@page import="com.Inventory.model.User"%>
<%@page import="com.Inventory.utill.SessionUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Order</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <%@ include file="header.jsp" %>
        
        <div class="pageuser">
            <div class="addremain">
                <div class="col-md-12">
                    <div id="alert-container" class="alert alert-danger" style="display:none;"></div>
                    <c:if test="${not empty message}">
                        <div class="alert ${alertClass}" role="alert">
                            ${message}
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="item">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading clearfix">
                            <strong>
                                <span class="glyphicon glyphicon-th"></span>
                                <span>Add Order</span>
                            </strong>
                        </div>
                        <div class="panel-body">
                            
                            <form method="post" action="add_order" onsubmit="return validateOrderForm()">
                                <div class="form-group">
                                    <label for="date">Date</label>
                                    <input type="text" class="form-control" name="date" id="order_date" readonly>
                                </div>
                                <table class="table table-bordered" id="order_table">
                                    <thead>
                                        <th> Item </th>
                                        <th> Qty </th>
                                        <th> Action</th>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <select class="form-control" name="items[]" required>
                                                    <option value="">Select Item</option>
                                                    <c:forEach var="item" items="${itemsList}">
                                                        <option value="${item.itemName}">${item.itemName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="number" class="form-control" name="quantities[]" placeholder="Quantity" required>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-success" onclick="addItemRow()">Add More</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <button type="submit" name="add_order" class="btn btn-primary">Add order</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            document.addEventListener('DOMContentLoaded', (event) => {
                const today = new Date().toISOString().split('T')[0];
                document.getElementById('order_date').value = today;
            });

            function addItemRow() {
                const table = document.getElementById('order_table').getElementsByTagName('tbody')[0];
                const newRow = table.insertRow();
                newRow.innerHTML = `
                    <td>
                        <select class="form-control" name="items[]" required>
                            <option value="">Select Item</option>
                            <c:forEach var="item" items="${itemsList}">
                                <option value="${item.itemName}">${item.itemName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <input type="number" class="form-control" name="quantities[]" placeholder="Quantity" required>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" onclick="removeItemRow(this)">Remove</button>
                    </td>
                `;
            }

            function removeItemRow(button) {
                const row = button.closest('tr');
                row.remove();
            }

            function validateOrderForm() {
                const alertContainer = document.getElementById('alert-container');
                const items = document.getElementsByName('items[]');
                const itemSet = new Set();
                for (let i = 0; i < items.length; i++) {
                    if (itemSet.has(items[i].value)) {
                        alertContainer.style.display = 'block';
                        alertContainer.innerHTML = 'Duplicate items are not allowed.';
                        return false;
                    }
                    itemSet.add(items[i].value);
                }
                alertContainer.style.display = 'none';
                return true;
            }
        </script>      
        <%@ include file="footer.jsp" %>
    </body>
</html>







