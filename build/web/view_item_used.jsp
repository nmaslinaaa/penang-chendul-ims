<%-- 
    Document   : view_item_used
    Created on : Nov 3, 2024, 4:17:07 PM
    Author     : User
--%>
<%@page import="com.Inventory.utill.PageAccessUtil"%>
<%@page import="com.Inventory.model.UsageItem"%>
<%@page import="com.Inventory.model.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.Inventory.utill.SessionUtil"%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    User user = SessionUtil.getCurrentUser(request);
    if (user == null) {
        SessionUtil.setErrorMessage(request, "Please login...");
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%
    if (!PageAccessUtil.pageRequireLevel(request, response, 2)) {
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Item Used</title>
    
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link rel="stylesheet" href="style.css" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <style>
        /* Custom styles for the popup */
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
    <div class="row">
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
                        <span>Used Items History</span>
                    </strong>
                </div>
                <div class="panel-body">
                    <c:if test="${empty usage_item}">
                        <!-- Modal for No Item Usage -->
                        <div class="modal fade" id="noItemModal" tabindex="-1" role="dialog" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <h4>No item usage available in the store.</h4>
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
                    <c:if test="${not empty usage_item}">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th class="text-center" style="width: 5%;">UsageItem ID</th>
                                    <th class="text-center" style="width: 5%;">#</th>
                                    <th class="text-center" style="width: 5%;">Item ID</th>
                                    <th style="width: 10%;">Item Name</th>
                                    <th style="width: 10%;">MOQ</th>
                                    <th class="text-center" style="width: 10%;">Used Quantity</th>
                                    <th class="text-center" style="width: 10%;">Usage Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="prevUsagesID" value="-1" />
                                <c:set var="rowCounter" value="1" />
                                <c:forEach var="usage" items="${usage_item}" varStatus="status">
                                    <tr>
                                        <td class="text-center">
                                            <c:if test="${prevUsagesID != usage.usagesID}">
                                                UI00${usage.usagesID}
                                                <c:set var="prevUsagesID" value="${usage.usagesID}" />
                                                <c:set var="rowCounter" value="1" />
                                            </c:if>
                                        </td>
                                        <td class="text-center">
                                            ${rowCounter}
                                            <c:set var="rowCounter" value="${rowCounter + 1}" />
                                        </td>
                                        <td class="text-center">IT00${usage.itemID}</td>
                                        <td>${usage.itemName}</td>
                                        <td>${usage.MOQ}</td>
                                        <td class="text-center">${usage.usedQty}</td>
                                        <td class="text-center">
                                            <fmt:formatDate value="${usage.usageDate}" pattern="yyyy-MM-dd"/>
                                        </td>
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
