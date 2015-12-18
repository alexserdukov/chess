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
    <script src="js/chess/authentication.js"></script>
    <script src="js/chess/restprotocol.js"></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Chess</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/login">Login</a></li>
                <li class="active"><a href="/register">Register</a></li>
            </ul>
        </div>
    </div>
</nav>

<body>

<div id="panel-info" class="alert alert-success" hidden role="alert"></div>

<div class="container form-signin">
    <h3 class="form-signin-heading">Registration</h3>
    <input id="email" type="email" class="form-control" placeholder="email" autofocus>
    <input id="username" class="form-control" placeholder="username" required>
    <input id="password" type="password" class="form-control" placeholder="password" required>
    <button id="btn-register" class="btn btn-lg btn-primary btn-block" type="button">Register</button>
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
