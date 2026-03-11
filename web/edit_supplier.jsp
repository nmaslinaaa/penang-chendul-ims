
<%-- 
    Document   : edit_supplier
    Created on : Jul 2, 2024, 6:28:17 PM
    Author     : User
--%>
<%@page import="com.Inventory.model.Supplier"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Supplier</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <%@include file = "header.jsp" %>
        <div class="pageuser">
            <div class="row">
                <div class="supplier">
                    <div class="col-md-12">
                        <c:if test="${not empty message}">
                            <div class="alert ${alertClass}" role="alert">
                                ${message}
                            </div>
                        </c:if>
                    </div>
                </div>
                
                <div class="supplier">
                     <div class="col-md-5">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <strong>
                                    <span class="glyphicon glyphicon-th"></span>
                                    <span>Editing ${supplier.supplierName}</span>
                                </strong>
                            </div>
                           
                            <div class="panel-body">
                                <form method="post" action="edit_supplier?id=${supplier.supplierID}">
                                    <div class="form-group">
                                        <input type="text" class="form-control" name="supplier-name" value="${supplier.supplierName}" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="email" class="form-control" name="supplier-email" value="${supplier.supplierEmail}" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" name="supplier-phone" value="${supplier.supplierPhone}" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Update Supplier</button>
                                </form>
                            </div>
                            

                        </div>
                    </div>
                </div>
              
            </div>
        </div>
        <%@include file = "footer.jsp" %>
    </body>
</html>


