<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Chess</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Chess</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/login">Login</a></li>
                <li><a href="/register">Register</a></li>
            </ul>
        </div>
    </div>
</nav>

<body>

<div class="container">
    <form id="login-form" class="form-signin" action="/j_spring_security_check" method="post" role="form">
        <h3 class="form-signin-heading">Login</h3>
        <input id="email" type="email" name="username" id="inputEmail" class="form-control" placeholder="email" required autofocus>
        <input id="password" type="password" name="password" id="inputPassword" class="form-control" placeholder="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>
</div>

<%-- FOOTER --%>
<footer class="footer">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a role="button">javamonkeys.com</a>
            </li>
        </ul>
    </div>
</footer>

</body>
</html>
