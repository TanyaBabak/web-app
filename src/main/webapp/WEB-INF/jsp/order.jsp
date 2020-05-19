<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="../jspf/header.jspf" %>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/bootstrap/css/validation.css">
    <link href="/bootstrap/static/css/bootstrap.css" rel="stylesheet">
    <link href="/bootstrap/static/css/bootstrap-theme.css" rel="stylesheet">
    <link href="/bootstrap/static/css/font-awesome.css" rel="stylesheet">
    <link href="/bootstrap/css/app.css" rel="stylesheet">
</head>
<body>

<div id="order">
    <c:if test="${CURRENT_MESSAGE != null }">
        <div class="alert alert-success hidden-print" role="alert">${CURRENT_MESSAGE}</div>
    </c:if>
    <h4 class="text-center">Order # ${order.id }</h4>
    <hr/>
    <ishop:product-table items="${order.items}" totalCost="${order.totalCost}" showActionColumn="false"/>
    <div class="row hidden-print">
    </div>
</div>
<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>
<script src="/bootstrap/js/modules/app.js"></script>
</body>
</html>