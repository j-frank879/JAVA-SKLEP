<!DOCTYPE html>
<html xmlns:jsp="http://java.sun.com/jsf/core">
<head>
    <title>Moja Strona</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
        }

        header {
            background-color: #333;
            color: #fff;
            padding: 20px;
        }

        header p {
            font-size: 24px;
            margin: 0;
        }

        nav {
            background-color: #f5f5f5;
            padding: 10px;
        }

        nav a {
            color: #333;
            text-decoration: none;
            padding: 5px 10px;
            margin-right: 10px;
            border-radius: 4px;
        }

        nav a:hover {
            background-color: #333;
            color: #fff;
        }

        main {
            flex: 1;
            padding: 20px;
        }

        footer {
            background-color: #f5f5f5;
            padding: 20px;
        }
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<header>
    <p> Sklep </p>
</header>

<nav>
    <a href="<c:url value='/product/list'/>">Products</a>
    <a href="<c:url value='/orders/list'/>">Orders</a>
</nav>

<main>
    ${content}
</main>

<footer>
    <p>Footer</p>
</footer>
</body>
</html>