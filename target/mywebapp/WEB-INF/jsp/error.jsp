<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="log" uri="/WEB-INF/tag/logout_loginTag.tld" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta charset="UTF-8"/>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/bootstrap/css/validation.css">
    <link href="/bootstrap/static/css/bootstrap.css" rel="stylesheet">
    <link href="/bootstrap/static/css/bootstrap-theme.css" rel="stylesheet">
    <link href="/bootstrap/static/css/font-awesome.css" rel="stylesheet">
    <link href="/bootstrap/css/app.css" rel="stylesheet">
</head>
<body>
<%@ include file="../jspf/header.jspf" %>
<div class="alert alert-danger hidden-print" role="alert">
    <h1>Code: ${statusCode}</h1>
    <c:choose>
        <c:when test="${statusCode == 403}">You don't have permissions to view this resource</c:when>
        <c:when test="${statusCode == 404}">Requested resource not found</c:when>
        <c:otherwise>Can't process this request! Try again later...</c:otherwise>
    </c:choose>
</div>
<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>

</body>
</html>
