<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Chess</title>
    <input type="hidden" id="user-id" value="${userId}"/>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/chess/authentication.js"></script>
    <script src="js/chess/profile.js"></script>
    <script src="js/chess/restprotocol.js"></script>
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
    <div class="panel-group form-signin">
        <div class="page-header">
            <h1>Profile</h1>
        </div>
        <div class="panel-body">
            <div class="panel-group">
                <p><img class="img-thumbnail" src="img/no_image.jpg"></p>
            </div>
            <div class="panel-group">
                <label for="email" class="control-label">email:</label>
                <input id="email" type="email" class="form-control" placeholder="email">
            </div>
            <div class="panel-group">
                <label for="password" class="control-label">password:</label>
                <input id="password" type="password" class="form-control" placeholder="password">
            </div>
            <div class="panel-group">
                <label for="username" class="control-label">username:</label>
                <input id="username" type="text" class="form-control" placeholder="username">
            </div>
            <div class="panel-group">
                <button id="btn-save" class="btn btn-lg btn-primary btn-block" type="submit">Save changes</button>
                <div id="panel-info" class="alert alert-success" hidden role="alert"></div>
            </div>
        </div>
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
