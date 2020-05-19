<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="/WEB-INF/tag/captchaTag.tld" %>
<%@ taglib prefix="log" uri="/WEB-INF/tag/logout_loginTag.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${current_locale}" scope="session"/>
<fmt:setBundle var="BundleContent" basename="resources"/>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta charset="UTF-8"/>
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/bootstrap/css/validation.css">
</head>
<body>
<%@ include file="../jspf/header.jspf" %>

<div id="highlighted-slider" class="container">
    <div class="item-slider" data-toggle="owlcarousel"
         data-owlcarousel-settings='{"singleItem":true, "navigation":true, "transitionStyle":"fadeUp"}'>
        <div class="item">
            <div class="row">
                <div class="col-md-6 col-md-push-6 item-caption">
                    <h2 class="h1 text-weight-light">
                        Welcome to <span class="text-primary">Shop of Electronic</span>
                    </h2>
                    <h4>
                        Looking to replace an old laptop, buy a new printer or get your favorite “techie” a gift?
                    </h4>
                    <p>There are lots of websites that sell tech and electronics,
                        but this website that will get you much better deals.
                        During this age of modern technology, new gadgets are being created each day. Some might only be
                        sold online and not in stores.
                        Follow this site to stay up to date with your electronics.</p>
                </div>
                <div class="col-md-6 col-md-pull-6 hidden-xs">
                    <img src="bootstrap/img/slides/slide1.png" alt="Slide 1" class="center-block img-responsive">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>
</body>
</html>