<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- fmt:setLocale konfiguruje uostawienia lokalne dla tej strony na polskie; używane przez fmt:formatNumber przy formatowaniu liczby--%>
<fmt:setLocale value="pl_PL"/>
<c:set var="content">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Orders</title>
</head>
<body>
<c:set var="user" value="${sessionScope.user}"/>
<c:if test="${user.role=='customer'}">
    <a href="<c:url value='/orders/add'/>">Make an order</a>
</c:if>
My current balance: <c:out value="${sessionScope.user.balance}"></c:out>

<table>
    <thead>
    <tr>
        <th>Product name</th>
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
                <c:if test="${orders.cancelled==false}">
                    <a href="<c:url value='/orders/cancel/${orders.id}'/>">Cancel</a>
                </c:if>
                <c:if test="${orders.paid==false}">
                    <a href="<c:url value='/orders/pay/${orders.id}'/>">Pay</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<table>

</table>

</br><a href="<c:url value='/orders/list'/>">Show only unpaid/not cancelled</a>
</br><a href="<c:url value='/orders/listAll'/>">Show all</a>
</br><a href="<c:url value='/orders/listCancelled'/>">Show cancelled only</a>
</br><a href="<c:url value='/orders/listPaid'/>">Show paid only</a>
</body>
</html>
</c:set>

<%@ include file="layout.jsp" %>

