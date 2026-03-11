<%-- 
    Document   : header
    Created on : Jul 7, 2024, 5:49:39 PM
    Author     : User
--%>
<%@page import="com.Inventory.model.User"%>
<%-- 
    Document   : header
    Created on : Jul 7, 2024, 5:49:39 PM
    Author     : User
--%>
<header id="header">
    <div class="logo pull-left"> Inventory System</div>
    <div class="header-content">
        <div class="header-date pull-left">
            <strong><%= new java.text.SimpleDateFormat("MMMM d, yyyy, h:mm a").format(new java.util.Date()) %></strong>
        </div>
        <div class="pull-right clearfix">
            <ul class="info-menu list-inline list-unstyled">
                <li class="profile">
                    <a href="#" data-toggle="dropdown" class="toggle" aria-expanded="false">
                        <img src="logouser.png" alt="user-image" class="img-circle img-inline">
                        <span><%= session.getAttribute("user") != null ? ((User) session.getAttribute("user")).getName() : "Guest" %> <i class="caret"></i></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="edit_account" title="edit account">
                                <i class="glyphicon glyphicon-user"></i>
                                Profile
                            </a>
                        </li>
                        <li class="last">
                            <a href="LogoutServlet">
                                <i class="glyphicon glyphicon-off"></i>
                                Logout
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</header>

<div class="sidebar">
    <c:if test="${sessionScope.user_level == 1}">
        <jsp:include page="admin_menu.jsp"/>
    </c:if>
 <c:if test="${sessionScope.user_level == 2}">
        <jsp:include page="special_menu.jsp"/>
    </c:if>
</div>
