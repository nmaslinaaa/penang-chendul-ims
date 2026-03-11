<%-- 
    Document   : daily_order
    Created on : Jul 2, 2024, 6:33:33 PM
    Author     : User
--%>
<%@page import="com.Inventory.model.OrderItem"%>
<%@page import="com.Inventory.model.User"%>
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
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily Order</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <%@ include file="header.jsp" %>

        <div class="pageuser">
             <div class="row">
                <div class="col-md-6">
                  <c:if test="${not empty alertMessage}">
                    <div class="alert alert-info" role="alert">
                        ${alertMessage}
                    </div>
                  </c:if>
                </div>
              </div>

              <div class="row">
                <div class="col-md-12">
                  <div class="panel panel-default">
                    <div class="panel-heading clearfix">
                      <strong>
                        <span class="glyphicon glyphicon-th"></span>
                        <span>View Daily Orders</span>
                      </strong>
                    </div>
                    <div class="panel-body">
                      <table class="table table-bordered table-striped">
                        <thead>
                          <tr>
                            <th class="text-center" style="width: 50px;">#</th>
                            <th> Item Name </th>
                            <th class="text-center" style="width: 15%;"> Supplier </th>
                            <th class="text-center" style="width: 15%;"> Order Quantity</th>
                            <th class="text-center" style="width: 15%;"> Date </th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach var="order" items="${orders}" varStatus="status">
                            <tr>
                              <td class="text-center"><c:out value="${status.index + 1}"/></td>
                              <td><c:out value="${order.itemName}"/></td>
                              <td class="text-center"><c:out value="${order.supplierName}"/></td>
                              <td class="text-center"><c:out value="${order.orderQty}"/></td>
                              <td class="text-center"><c:out value="${order.orderDate}"/></td>
                            </tr>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>

        </div>
      
        <%@ include file="footer.jsp" %>
    </body>
</html>



