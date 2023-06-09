<%@ page import="wipb.ee.jspdemo.web.bean.UserBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<c:set var="user" value="${sessionScope.user}"/>
<c:choose>
    <c:when test="${user==null}">
        <table>
            <tr>
                <td>  <a href="<c:url value='/product/list'/>">Products</a></td>
                <td>  <a href="<c:url value='/registry'/>">Registry</a></td>
                <td>  <a href="<c:url value='/login'/>">Login</a></td>

            </tr>

        </table>
    </c:when>
    <c:when test="${user.role=='customer'}">
        <table>
            <tr>
                <td>  <a href="<c:url value='/product/list'/>">Products</a><br/></td>
                <td>  <a href="<c:url value='/orders/list'/>">My Orders</a><br/></td>
                <td>  <a href="<c:url value='/logout'/>">Logout</a><br/></td>

            </tr>
        </table>
    </c:when>
    <c:when test="${user.role=='worker'}">
        <table>
            <tr>
                <td>  <a href="<c:url value='/product/list'/>">Products</a></td>
                <td>  <a href="<c:url value='/orders/list'/>"> Orders</a></td>
                <td>  <a href="<c:url value='/logout'/>">Logout</a></td>

            </tr>

        </table>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <td>  <a href="<c:url value='/product/list'/>">Products</a></td>
                <td>  <a href="<c:url value='/registry'/>">Registry</a></td>
                <td>  <a href="<c:url value='/login'/>">Login</a></td>

            </tr>

        </table>
    </c:otherwise>
</c:choose>

