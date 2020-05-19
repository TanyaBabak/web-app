<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="log" uri="/WEB-INF/tag/logout_loginTag.tld" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta charset="UTF-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/bootstrap/css/validation.css">
</head>
<body>
<%@ include file="../jspf/header.jspf" %>
<div class="container">
    <div class="row main-form">
        <form action="/login" method="post">
            <div class="form-group">
                <label class="cols-sm-2 control-label ">Your Login</label>
                <div class="cols-sm-10">
                    <div class="input-group">
                        <input type="text" class="form-control" name="login_login" id="login_login"
                               placeholder="Login"/>
                        <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label  class="cols-sm-2 control-label ">Password</label>
                <div class="cols-sm-10">
                    <div class="input-group">
                        <input type="password" class="form-control" name="current-password"
                               id="current-password" autocomplete="on" placeholder="Password"/>
                        <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                    </div>
                    <div class="errorLogin">${errors.login}</div>
                </div>
            </div>
            <input type="submit" class="validateBtn btn btn-success btn-lg" value='Log in'>
    </form>
</div>
</div>
<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>
</body>
</html>
