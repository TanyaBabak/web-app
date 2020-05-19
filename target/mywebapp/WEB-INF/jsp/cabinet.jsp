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
<h3>${CURRENT_ACCOUNT.name}</h3>

<img alt src="${sessionScope.path}">

<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>

</body>
</html>
