<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.Inventory.utill.SessionUtil"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
            font-weight: 400;
            overflow-x: hidden;
            overflow-y: auto;
            height: 100%;
            width: 100%;
            background: url('login.jpeg') no-repeat center center fixed;
            background-size: cover;
        }
        .container-fluid {
            padding-right: 15px;
            padding-left: 15px;
            margin-right: auto;
            margin-left: auto;
        }
        .login-page {
            width: 350px;
            margin: 2% auto;
            padding: 20px;
            background-color: rgb(188 188 188 / 89%);
            border: 2px solid #06973b;
            border-radius: 10px;
        }
        .login-page .text-center {
            margin-bottom: 10px;
        }
        .text-center {
            text-align: center;
        }
        form {
            display: block;
            margin-top: 0em;
            unicode-bidi: isolate;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .btn-danger {
            background-color: #ed5153;
            color: #fff;
            border-color: #d43f3a;
        }
        .btn {
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            background-image: none;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        .form-control {
            display: block;
            width: 100%;
            height: 25px;
            padding: 6px 2px;
            font-size: 14px;
            line-height: 1.42857143;
            background-color: #fff;
            background-image: none;
            color: #646464;
            border: 1px solid #e6e6e6;
            border-radius: 3px;
        }
        .alert {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        .alert-danger {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
        div.loginHeader{
	text-align: center;
	margin-bottom: 50px;
	margin-top: 20px;
}

        div.loginHeader h1{
	font-size: 80px;
	color:lightgreen;
	padding: 0px;
	margin: 0px;
	text-shadow: 3px 3px 6px black;
}
    </style>
</head>
<body>
    <div class="loginHeader">
			<h1>PENANG CHENDUL</h1>
    </div>
    <div class="login-page">
        <div class="text-center">
            <h1>Login Panel</h1>
            <h4>Inventory Management System</h4>
        </div>
        <%
            String errorMessage = SessionUtil.getErrorMessage(request);
            if (errorMessage != null) {
                SessionUtil.clearErrorMessage(request);
        %>
            <div class="alert alert-danger">
                <%= errorMessage %>
            </div>
        <% } %>
        <form method="post" action="LoginServlet">
            <div class="form-group">
                <label for="username" class="control-label">Username</label>
                <input type="name" class="form-control" name="username" placeholder="Username" required>
            </div>
            <div class="form-group">
                <label for="Password" class="control-label">Password (eg; Demo1006@)</label>
                <input type="password"  name="password" class="form-control" placeholder="Password" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-danger" style="border-radius:0%">Login</button>
            </div>
        </form>
    </div>
</body>
</html>
