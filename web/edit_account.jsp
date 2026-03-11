<%-- 
    Document   : edit_account
    Created on : Jul 2, 2024, 6:28:17 PM
    Author     : User
--%>

<%@page import="com.Inventory.model.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.Inventory.utill.SessionUtil"%>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.

    User currentUser = SessionUtil.getCurrentUser(request);
    if (currentUser == null) {
        SessionUtil.setErrorMessage(request, "Please login...");
        response.sendRedirect("login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Account</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="style.css" />
    
    <script>
        let isFormDirty = false;
        let clickedLinkUrl = ""; // To store the clicked link's URL

        function trackFormChanges() {
            const form = document.querySelector('form');
            const inputs = form.querySelectorAll('input');

            inputs.forEach(input => {
                input.addEventListener('input', () => {
                    isFormDirty = true;
                });
            });
        }

        function warnBeforeNavigate(event) {
    if (isFormDirty) {
        event.preventDefault(); // Prevent navigation
        clickedLinkUrl = event.target.href; // Store the clicked link's URL
        $('#unsavedChangesModal').modal('show'); // Show the modal

        // Remove this line to avoid showing the URL in the modal
        // document.querySelector('#clickedLinkUrl').textContent = clickedLinkUrl;

        // Add an event listener to continue navigation when user confirms
        document.querySelector('#confirmLeaveBtn').onclick = function () {
            isFormDirty = false; // Reset the flag
            window.location.href = clickedLinkUrl; // Redirect to the stored URL
        };
    }
}

        function attachSidebarListeners() {
            const sidebarLinks = document.querySelectorAll('.sidebar a');
            sidebarLinks.forEach(link => {
                link.addEventListener('click', warnBeforeNavigate);
            });
        }

        document.addEventListener('DOMContentLoaded', () => {
            trackFormChanges();
            attachSidebarListeners();
        });
    </script>

</head>
<body>
<%@ include file="header.jsp" %>
<div class="pageuser">
    <div class="row">
        <div class="col-md-12">
            <c:if test="${not empty message}">
                <div class="alert ${alertClass}" role="alert">
                    ${message}
                </div>
            </c:if>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading clearfix">
                    <span class="glyphicon glyphicon-edit"></span>
                    <span>Edit My Account</span>
                </div>
                <div class="panel-body">
                    <form method="post" action="edit_account" class="clearfix">
                        <div class="form-group">
                            <label for="name" class="control-label">Name</label>
                            <input type="name" class="form-control" name="name" value="<%= currentUser.getName() %>">
                        </div>
                        <div class="form-group">
                            <label for="username" class="control-label">Username</label>
                            <input type="text" class="form-control" name="username" value="<%= currentUser.getUsername() %>">
                        </div>
                        <div class="form-group clearfix">
                            <a href="change_password" title="change password" class="btn btn-danger pull-right">Change Password</a>
                            <button type="submit" name="update" class="btn btn-info">Update</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal for Unsaved Changes -->
<div class="modal fade" id="unsavedChangesModal" tabindex="-1" role="dialog" aria-labelledby="unsavedChangesModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="unsavedChangesModalLabel">Unsaved Changes</h4>
            </div>
            <div class="modal-body">
                You have unsaved changes. Are you sure you want to leave this page without saving?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" id="confirmLeaveBtn">Leave Page</button>
            </div>
        </div>
    </div>
</div>

<%@include file="footer.jsp" %>
</body>
</html>
