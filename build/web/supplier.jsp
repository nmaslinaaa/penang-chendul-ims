<%-- 
    Document   : supplier
    Created on : Jul 2, 2024, 1:09:24 AM
    Author     : User
--%>
<%@page import="com.Inventory.model.Supplier"%>
<%@page import="com.Inventory.model.User"%>
<%@page import="com.Inventory.utill.SessionUtil"%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
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
<%
    // Check user level and redirect if not authorized
    if (!PageAccessUtil.pageRequireLevel(request, response, 1)) {
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Supplier</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
        <link rel="stylesheet" href="style.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <%@include file = "header.jsp" %>
        <div class="supplier">
               <div class="page">
            <div class="container">
                <div class="rowsuppplier">
                    <div class="col-md-5">
                        <c:if test="${not empty message}">
                            <div class="alert ${alertClass} alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                ${message}
                            </div>
                        </c:if>
                        <!-- Add New Supplier panel -->
                        <div class="supplier">
                            <div class="panel panel-default">
                            <div class="panel-heading">
                                <strong>
                                    <span class="glyphicon glyphicon-th"></span>
                                    <span>Add New Supplier</span>
                                </strong>
                            </div>
                            <div class="panel-body">
                                <form method="post" action="supplier">
                                    <div class="form-group">
                                        <input type="hidden" name="action" value="add">
                                        <input type="text" class="form-control" name="supplier-name" placeholder="Supplier Name" required><br>
                                        <input type="email" class="form-control" name="supplier-email" placeholder="Email" required><br>
                                        <input type="text" class="form-control" name="supplier-phone" placeholder="Phone Number" required>
                                    </div>
                                    <button type="submit" name="add_supplier" class="btn btn-primary">Add Supplier</button>
                                </form>
                            </div>
                        </div>
                        </div>
                        
                    </div>
                    <div class="supplier2">
                        <div class="col-md-7">
                          <!-- All Suppliers panel -->
                          <div class="panel panel-default">
                              <div class="panel-heading">
                                  <strong>
                                      <span class="glyphicon glyphicon-th"></span>
                                      <span>All Suppliers</span>
                                  </strong>
                              </div>
                              <div class="panel-body">
                                  <table class="table table-bordered table-striped table-hover">
                                      <thead>
                                          <tr>
                                              <th class="text-center" style="width: 50px;">#</th>
                                              <th>Supplier Name</th>
                                              <th>Email</th>
                                              <th>Phone Number</th>
                                              <th class="text-center" style="width: 100px;">Actions</th>
                                          </tr>
                                      </thead>
                                      <tbody>
                                          <c:forEach var="supplier" items="${suppliers}" varStatus="status">
                                              <tr>
                                                  <td class="text-center">${status.index + 1}</td>
                                                  <td>${supplier.supplierName}</td>
                                                  <td>${supplier.supplierEmail}</td>
                                                  <td>${supplier.supplierPhone}</td>
                                                  <td class="text-center">
                                                      <div class="btn-group">
                                                          <a href="edit_supplier?id=${supplier.supplierID}" class="btn btn-xs btn-warning" data-toggle="tooltip" title="Edit">
                                                              <span class="glyphicon glyphicon-edit"></span>
                                                          </a>
                                                          <button type="button" class="btn btn-xs btn-danger" data-toggle="tooltip" title="Remove" onclick="confirmDelete(${supplier.supplierID})">
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
        </div>
     
        <%@include file = "footer.jsp" %>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="deleteModalLabel">Confirm Delete</h4>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to delete this supplier? You will lose all the item details supplied by this supplier.
                    </div>
                    <div class="modal-footer">
                        <form method="post" action="supplier">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="supplier-id" id="supplier-id" value="">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-danger">Yes</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            function confirmDelete(supplierID) {
                $('#supplier-id').val(supplierID);
                $('#deleteModal').modal('show');
            }
        </script>
    </body>
</html>



