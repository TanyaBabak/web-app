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
<%--    <link href="/bootstrap/static/css/bootstrap.css" rel="stylesheet">--%>
<%--    <link href="/bootstrap/static/css/bootstrap-theme.css" rel="stylesheet">--%>
<%--    <link href="/bootstrap/static/css/font-awesome.css" rel="stylesheet">--%>
    <link href="/bootstrap/css/app.css" rel="stylesheet">
</head>
<body class="m-3" data-page="catalogDB">
<%@ include file="../jspf/header.jspf" %>
<header class="cd-header">
    <h1>Electro-shop</h1>
</header>
<main class="cd-main-content">

    <div class="container">
        <br>
        <ul class="nav nav-pills">
            <li class="active"><a rel="nofollow" href="/catalog"><fmt:message key="Catalog" bundle="${BundleContent}"/></a></li>
        </ul>
        <br>
    </div>
    <div id="filters" class="col-md-12">
        <div class="btn-group">
            <button type="button" name="category" data-category="All Categories"
                    class="btn btn-default js-category">All Categories</button>
            <c:forEach items="${categories}" var="category">
                <button type="button" name="category" data-category="${category.key}"
                        class="btn btn-default js-category">${category.key}</button>
            </c:forEach>
        </div>
        <br>
        <br>
        <form id="filters-form" role="form">
            <div class="container">

                <div class="form-group col-md-4">

                    <label for="records"><fmt:message key="Select records per page:" bundle="${BundleContent}"/></label>

                    <select class="form-control" id="records" name="recordsPerPage">
                        <option value="2" selected>2</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                    </select>
                </div>
            </div>
            <div class="col-md-4">
                <h4><fmt:message key="Manufacturer" bundle="${BundleContent}"/></h4>
                <div id="manufacturers">
                    <c:forEach items="${manufacturers}" var="manufacturer">
                        <div class="checkbox"><label><input type="checkbox" name="manufacturers[]"
                                                            value="${manufacturer.getManufactureName()}"> ${manufacturer.getManufactureName()}
                        </label></div>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-4">
                <h4><fmt:message key="Filter by price" bundle="${BundleContent}"/></h4>
                <div id="prices-label">200 - 70000 $</div>
                <br/>
                <input type="hidden" id="min-price" name="min_price" value="2000">
                <input type="hidden" id="max-price" name="max_price" value="70000">
                <div id="prices" class="ui-slider ui-slider-horizontal ui-widget ui-widget-content ui-corner-all">
                    <div class="ui-slider-range ui-widget-header ui-corner-all" style="left: 0%; width: 100%;">
                    </div>
                    <span class="ui-slider-handle ui-state-default ui-corner-all" tabindex="0" style="left: 0%;">
                        </span><span class="ui-slider-handle ui-state-default ui-corner-all" tabindex="0"
                                     style="left: 100%;">
                        </span>
                </div>
            </div>
            <div class="col-md-4">
                <h4><fmt:message key="Sort" bundle="${BundleContent}"/></h4>
                <br>
                <select id="sort" name="sort" class="form-control">
                    <option value="product_name_asc"><fmt:message key="By name," bundle="${BundleContent}"/> A-Z</option>
                    <option value="product_name_desc"><fmt:message key="By name," bundle="${BundleContent}"/> Z-A</option>
                    <option value="price_asc"><fmt:message key="By price, cheap first" bundle="${BundleContent}"/></option>
                    <option value="price_desc"><fmt:message key="By price, first dear" bundle="${BundleContent}"/></option>
                </select>
            </div>
        </form>
    </div>
    <br/>
    <br/>
    <section  class="cd-gallery catalogList">
    </section>
</main>

<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>
<script src="/bootstrap/js/modules/app.js"></script>
<script src="/bootstrap/js/modules/catalogDB.js" type="text/javascript"></script>
<script src="/bootstrap/js/modules/main.js" type="text/javascript"></script>
<ishop:add-product-popup/>
</body>
</html>