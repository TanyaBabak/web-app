<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${current_locale}" scope="session"/>
<fmt:setBundle var="BundleContent" basename="resources"/>

<c:choose>
    <c:when test="${not empty products}">
        <ul id="MixItUp230A90" disabled="disabled">
            <c:forEach var="product" items="${products}">
                <li class="mix" style="display: inline-block;">
                    <div id="product<c:out value="${product.id}"/>">
                        <div class="thumbnail">
                            <img src="${product.link}">
                        </div>
                        <h2 class="name"><c:out value="${product.productName}"/></h2>
                        <div>${product.productDescription}</div>
                        <div class="price"><c:out value="${product.price}"/></div>
                        <a class="btn btn-primary pull-right buy-btn" data-id-product="${product.id}"><fmt:message key="Buy" bundle="${BundleContent}"/></a>
                    </div>
                </li>
            </c:forEach>
            <li class="gap"></li>
            <li class="gap"></li>
            <li class="gap"></li>
        </ul>
        <nav aria-label="Navigation for product">
            <ul id="pagination" class="pagination">
                <c:if test="${currentPage ne 1}">
                    <li data-page="1"><a href>&laquo</a></li>
                    <li data-page="${currentPage -1}"/>
                    <a href>&lt</a>
                </c:if>
                <c:forEach begin="1" end="${numberPages}" var="count">
                    <c:choose>
                        <c:when test="${currentPage eq count}">
                            <li data-page="count" class="active "><a href>${count}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li data-page="count"><a href>${count}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage  ne numberPages}">
                    <li data-page=" ${currentPage+1}"><a href>&gt;</a></li>
                    <li data-page="{numberPages}"><a href>&raquo</a></li>
                </c:if>
            </ul>
        </nav>
    </c:when>
    <c:otherwise>
        <h2>No results found</h2>
    </c:otherwise>
</c:choose>


