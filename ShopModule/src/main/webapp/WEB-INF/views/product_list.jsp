<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- fmt:setLocale konfiguruje ustawienia lokalne dla tej strony na polskie; używane przez fmt:formatNumber przy formatowaniu liczby--%>
<fmt:setLocale value="pl_PL"/>
<c:set var="content">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Products</title>
</head>
<body>
<c:set var="user" value="${sessionScope.user}"/>
<c:if test="${user.role=='worker'}">
    <a href="<c:url value='/product/add'/>">Add a product</a>
</c:if>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items='${productList}' var='product'>
        <tr>
            <td>${fn:escapeXml(product.name)}</td>
            <td>
                    <fmt:formatNumber value="${product.price}" type="number"/>
            <td>
                    <%-- c:url dodaje do url nazwę aplikacji (context root) oraz identifykator sesji jsessionid jeśli sesja jest włączona i brak obsługi ciasteczek --%>
                <a href="<c:url value='/product/edit/${product.id}'/>">Edit</a>,
                <a href="<c:url value='/product/remove/${product.id}'/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
</c:set>

<%@ include file="layout.jsp" %>
