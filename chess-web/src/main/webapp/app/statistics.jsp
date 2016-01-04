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
    <script src="js/chess/restprotocol.js"></script>
    <script src="js/chess/statistics.js"></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Chess</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/index">Game</a></li>
                <li class="active"><a href="/statistics">Statistics</a></li>
                <li><a href="/about">About</a></li>
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
        <h1>Statistics</h1>
    </div>

    <p id="user-name" class="lead text-center"></p>
    <div class="container">
        <table class="table table-bordered tab">
            <thead>
            <tr><th>key</th><th>value</th></tr>
            </thead>
            <tbody id="user-statistics">
            </tbody>
        </table>
    </div>

    <p class="lead text-center">Global statistics</p>
    <div class="container">
        <table class="table table-bordered tab">
            <thead>
                <tr><th>place</th><th>user</th><th>wins</th><th>loses</th></tr>
            </thead>
            <tbody id="global-statistics">
            </tbody>
        </table>
    </div>
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
