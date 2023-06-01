<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- fmt:setLocale konfiguruje uostawienia lokalne dla tej strony na polskie; używane przez fmt:formatNumber przy formatowaniu liczby--%>
<fmt:setLocale value="pl_PL"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Products</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Count</th>
        <th>Total</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items='${ordersList}' var='orders'>
        <tr>
            <td>${fn:escapeXml(orders.productName)}</td>
            <td>
                <fmt:formatNumber value="${orders.productCount}" type="number"/>
            </td>
            <td>
                <fmt:formatNumber value="${orders.total}" type="number"/>
            </td>
            <td>
                    <%-- c:url dodaje do url nazwę aplikacji (context root) oraz identifykator sesji jsessionid jeśli sesja jest włączona i brak obsługi ciasteczek --%>
                <a href="<c:url value='/orders/cancel/${orders.id}'/>">Cancel</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="<c:url value='/orders/add'/>">Make an order</a>
</body>
</html>
