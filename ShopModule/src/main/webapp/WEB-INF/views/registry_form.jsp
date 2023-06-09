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
<h3>Create new Account</h3>
<form method="post">
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
        <tr>
            <td>Email:</td>
            <td><input type="text" name="email" > </td>
            <td><span class="error">${errors.email}</span></td>
        </tr>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name"  > </td>
            <td><span class="error">${errors.name}</span></td>
        </tr>

    </table>
    <input type="submit" value="Register">
</form>
</body>
</html>
</c:set>

<%@ include file="layout.jsp" %>