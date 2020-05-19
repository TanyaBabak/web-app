<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/bootstrap/css/style.css">
    <link rel="stylesheet" href="/bootstrap/css/reset.css">
    <link rel="stylesheet" href="/components/jquery-ui/jquery-ui.min.css" type="text/css">
    <link href="/bootstrap/css/main.css" rel="stylesheet" type="text/css">
    <script src="/bootstrap/js/modernizr.js"></script>
    <link href="/bootstrap/static/css/bootstrap.css" rel="stylesheet">
    <link href="/bootstrap/static/css/bootstrap-theme.css" rel="stylesheet">
    <link href="/bootstrap/static/css/font-awesome.css" rel="stylesheet">
    <link href="/bootstrap/css/app.css" rel="stylesheet">
</head>
<body class="m-3">
<%@ include file="../jspf/header.jspf" %>
<div id="shoppingCart">
    <c:if test="${CURRENT_ACCOUNT == null}">
        <div class="alert alert-warning hidden-print" role="alert">To make order, please sign in</div>
    </c:if>
    <ishop:product-table items="${CURRENT_SHOPPING_CART.items}" totalCost="${CURRENT_SHOPPING_CART.totalCost}"
                         showActionColumn="true"/>
    <div class="row hidden-print">
        <div class="col-md-4 col-md-offset-4 col-lg-2 col-lg-offset-5">
            <c:choose>
                <c:when test="${CURRENT_ACCOUNT != null }">
                    <a href="javascript:void(0);" class="post-request btn btn-primary btn-block" data-url="/order">Make
                        order</a>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>

<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>
<script src="/bootstrap/js/modules/app.js"></script>
<script src="bootstrap/js/modules/catalogDB.js" type="text/javascript"></script>
<script src="bootstrap/js/modules/main.js" type="text/javascript"></script>
</body>
</html>