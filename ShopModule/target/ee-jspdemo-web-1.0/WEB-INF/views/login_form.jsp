
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registry</title>
    <style>.error { color: red; }</style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h3>Registration</h3>
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


    </table>
    <input type="submit" value="Login">
</form>
</body>
</html>
