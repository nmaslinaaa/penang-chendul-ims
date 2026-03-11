<%-- 
    Document   : item
    Created on : Jul 2, 2024, 2:23:49 AM
    Author     : User
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.Inventory.model.Item"%>
<%@ page import="com.Inventory.model.User"%>
<%@ page import="com.Inventory.utill.SessionUtil"%>
<%@ page import="com.Inventory.utill.PageAccessUtil"%>

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
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>All Items</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
     <%@ include file="header.jsp" %>
    <div class="pageitem">
        <div class="container">
            <div class="rowitem">
            </div>
            <div class="rowitem">
                <div class="item">
                    <div class="col-md-12"> 
                        <c:if test="${not empty message}">
                            <div class="alert ${message.contains('Successfully') ? 'alert-success' : 'alert-danger'} alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                ${message}
                            </div>
                        </c:if>
                        <div class="panel panel-default">
                            <div class="panel-heading clearfix">
                                <div class="pull-right">
                                    <a href="add_item" class="btn btn-primary">Add New</a>
                                </div>
                            </div>
                            <div class="panel-body">

                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th class="text-center" style="width: 50px;">#</th>
                                            <th style="width: 20%;"> Item Name </th>
                                            <th style="width: 20%;"> Item MOQ </th>
                                            <th style="width: 20%;"> Item Price </th>
                                            <th class="text-center" style="width: 15%;"> Suppliers </th>
                                            <th class="text-center" style="width: 15%;"> Current-Quantity </th>
                                            <th class="text-center" style="width: 20%;"> Item Added </th>
                                            <th class="text-center" style="width: 70px;"> Actions </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            int index = 1; // Initialize a counter variable
                                        %>
                                        <c:forEach var="item" items="${items}">
                                            <tr>
                                                <td class="text-center"><%= index++ %></td> <!-- Display the counter and increment it -->
                                                <td>${item.itemName}</td>
                                                <td>${item.MOQ}</td>
                                                <td>RM ${item.itemPrice}</td>
                                                <td class="text-center">${item.supplierName}</td>
                                                <td class="text-center">${item.currentQty}</td>
                                                <td class="text-center"><fmt:formatDate value="${item.dateAdded}" pattern="yyyy-MM-dd"/></td>
                                                <td class="text-center">
                                                    <div class="btn-group">
                                                        <a href="edit_item?id=${item.itemID}" class="btn btn-info btn-xs" title="Edit" data-toggle="tooltip">
                                                            <span class="glyphicon glyphicon-edit"></span>
                                                        </a>
                                                        <button type="button" class="btn btn-danger btn-xs" title="Delete" data-toggle="tooltip" onclick="confirmDelete(${item.itemID})">
                                                            <span class="glyphicon glyphicon-trash"></span>
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
        </div>
    </div>
  
    <%@ include file="footer.jsp" %>

    <!-- Delete Confirmation Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="deleteModalLabel">Confirm Delete</h4>
                </div>
                <div class="modal-body">
                    Are you sure you want to delete this item? You will lose all the item details.
                </div>
                <div class="modal-footer">
                    <form method="post" action="item">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="item-id" id="item-id" value="">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-danger">Yes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function confirmDelete(itemID) {
            $('#item-id').val(itemID);
            $('#deleteModal').modal('show');
        }
    </script>
</body>
</html>

