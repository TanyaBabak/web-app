<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="/WEB-INF/tag/captchaTag.tld" %>
<%@ taglib prefix="log" uri="/WEB-INF/tag/logout_loginTag.tld" %>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta charset="UTF-8"/>
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="/bootstrap/css/validation.css">
</head>
<body>
<%@ include file="../jspf/header.jspf" %>
<div class="container">
    <div class="row main-form">
        <form class="formWithValidation" action="/registration" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="firstName" class="cols-sm-2 control-label "><fmt:message key="Your Name" bundle="${BundleContent}"/></label>
                <div class="cols-sm-10">
                    <div class="input-group nameBlock ">
                        <input type="text" class="form-control firstName field" name="firstName"
                               value="${formBean.name}" id="firstName"
                               placeholder="Enter your Name"/>
                        <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                    </div>
                </div>
                <div class="errorNameIsEmpty">${error.name}</div>
                <div class="errorNameOnRegex"></div>
            </div>

            <div class="form-group">
                <label for="surname" class="cols-sm-2 control-label "><fmt:message key="Your Surname" bundle="${BundleContent}"/></label>
                <div class="cols-sm-10">
                    <div class="input-group surnameBlock">
                        <input type="text" class="form-control surname field" name="surname"
                               value="${formBean.surname }" id="surname"
                               placeholder="Enter your surname"/>
                        <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                    </div>
                </div>
                <div class="errorSurnameIsEmpty">${error.surname}</div>
                <div class="errorSurnameOnRegex"></div>
            </div>

            <div class="form-group">
                <label for="email" class="cols-sm-2 control-label ">Your Email</label>
                <div class="cols-sm-10">
                    <div class="input-group emailBlock">
                        <input type="text" class="form-control email field" name="email" value="${formBean.email }"
                               id="email"
                               placeholder="Enter your Email"/>
                        <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                    </div>
                </div>
                <div class="errorEmail">${error.email}</div>
            </div>

            <div class="form-group">
                <label for="login" class="cols-sm-2 control-label ">Your Login</label>
                <div class="cols-sm-10">
                    <div class="input-group loginBlock">
                        <input type="text" class="form-control login field" name="login" value="${formBean.login}"
                               id="login"
                               placeholder="Enter your login"/>
                        <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                    </div>
                </div>
                <div class="errorLoginIsEmpty">${error.loginExist} ${error.login} </div>
            </div>

            <div class="form-group">
                <label for="password" class="cols-sm-2 control-label ">Password</label>
                <div class="cols-sm-10">
                    <div class="input-group passwordBlock">
                        <input type="password" class="form-control password field" name="password"
                               value="${formBean.password}" id="password"
                               placeholder="Enter your Password"/>
                        <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                    </div>
                </div>
                <div class="errorPasswordIsEmpty">${error.password}</div>
            </div>

            <div class="form-group">
                <label for="passwordConfirmation" class="cols-sm-2 control-label ">Confirm Password</label>
                <div class="cols-sm-10">
                    <div class="input-group passwordConfirmationBlock">
                        <input type="password" class="form-control passwordConfirmation field"
                               name="passwordConfirmation" value="${formBean.passwordConfirmation}"
                               id="passwordConfirmation" placeholder="Confirm your Password"/>
                        <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                    </div>
                </div>
                <div class="errorPassword error">${error.passwordConfirmation}</div>
            </div>

            <td><ex:img url="captchaServlet" hiddenField="${hiddenField}"/></td>
            <div class="form-group ">

                <div class="form-group">
                    <span>Prove you are a human</span><span class="validation-block" id="captcha-validation-msg"></span>
                    <input type="text" class="form-control" id="captcha" value="${formBean.captcha}" name="captcha">
                    <div class="errorCaptcha">${error.captcha}</div>
                </div>
                <div class="form-group">
                    <span>Upload your profile avatar</span><span class="validation-block"
                                                                 id="avatar-validation-msg"></span>
                    <input type="file" class="form-control" id="avatar"
                           name="avatar">
                    <div class="errorAvatar">${error.avatar}</div>
                </div>
                <input type="submit" class="validateBtn btn btn-success btn-lg" value='Enter'>
                <div class="errorTimeOut">${error.timeOut}</div>
            </div>
            <strong>You have own account?</strong>
            <a class="Danger link" href="/login"/>    <h4>Log in</h4>

        </form>
    </div>
</div>

<script src="/bootstrap/js/vendor/jquery.js"></script>
<script src="components/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="/bootstrap/bootstrap.js"></script>
</body>
</html>
