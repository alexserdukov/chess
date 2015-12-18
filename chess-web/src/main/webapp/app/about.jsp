<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Chess</title>
    <input type="hidden" id="user-id" value="${userId}"/>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/chess/authentication.js"></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Chess</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/index">Game</a></li>
                <li><a href="/statistics">Statistics</a></li>
                <li class="active"><a href="/about">About</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button">${userName}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/profile">profile</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a id="btn-logout">logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<body>

<div class="container">
    <div class="page-header">
        <h1>About</h1>
    </div>
    <p class="lead text-center">This project was created by monkeys for monkeys. Enjoy chess game, learn Java and eat bananas!</p>
    <%--<div class="panel-group">--%>
        <p class="text-center"><img class="img-thumbnail" src="img/javamonkey.jpg"></p>
    <%--</div>--%>
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
