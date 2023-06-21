<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <style>.error { color: red; }</style>
    </head>
    <body>
    <jsp:include page="navbar.jsp"/>
        <h3>Product</h3>
        <form method="post">
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="name" value="${fn:escapeXml(name)}" ></td>
                    <td><span class="error">${errors.name}</span></td>
                </tr>
                <tr>
                    <td>Cena:</td>
                    <td><input type="text" name="price" value="${fn:escapeXml(price)}" > </td>
                    <td><span class="error">${errors.price}</span></td>
                </tr>
            </table>
            <input type="submit" value="Save"> 
        </form>
    </body>
</html>

