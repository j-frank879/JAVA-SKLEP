<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="content">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register</title>
    <style>.error { color: red; }</style>
</head>
<body>
<h3>Login</h3>
<form method="post">
  
    <p class="error"><%= request.getAttribute("error_message_login")!=null ? request.getAttribute("error_message_login") : ""%></p>
        <table>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login"  ></td>
            <td><span class="error">${errors.login}</span></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="text" name="password"> </td>
            <td><span class="error">${errors.password}</span></td>
        </tr>


    </table>
    <input type="submit" value="Login">
</form>
</body>
</html>
</c:set>

<%@ include file="layout.jsp" %>