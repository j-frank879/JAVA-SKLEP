<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="content">
    <!DOCTYPE html>
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>
        <style>.error {
            color: red;
        }</style>
    </head>
    <body>
    <h3>Order</h3>
    <form method="post">
        <table>
            <tr>
                <td>Product name:</td>
                <td><input type="text" name="productName" value="${fn:escapeXml(productName)}"></td>
                <td><span class="error">${errors.name}</span></td>
            </tr>
            <tr>
                <td>Count:</td>
                <td><input type="text" name="productCount" value="${fn:escapeXml(productCount)}"></td>
                <td><span class="error">${errors.price}</span></td>
            </tr>
        </table>
        <input type="submit" value="Save">
    </form>
    List of our Products:
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items='${productList}' var='product'>
            <tr>
                <td>
                        ${fn:escapeXml(product.name)}
                </td>
                <td>
                        <fmt:formatNumber value="${product.price}" type="number"/>
                <td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </body>
    </html>
</c:set>

<%@ include file="layout.jsp" %>

