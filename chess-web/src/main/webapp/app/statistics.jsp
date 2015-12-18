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

    <p class="lead text-center">Alexander Filippov</p>
    <div class="container">
        <table class="table table-bordered tab">
            <thead>
            <tr><th>key</th><th>value</th></tr>
            </thead>
            <tbody>
            <tr><td>games (total)</td><td>100</td></tr>
            <tr><td>games (won)</td><td>67</td></tr>
            <tr><td>games (lost)</td><td>33</td></tr>
            </tbody>
        </table>
    </div>

    <p class="lead text-center">Global statistics</p>
    <div class="container">
        <table class="table table-bordered tab">
            <thead>
                <tr><th>place</th><th>user</th><th>wins</th></tr>
            </thead>
            <tbody>
                <tr><td>1</td><td>kasparov, garry</td><td>225</td></tr>
                <tr><td>2</td><td>karpov, anatoliy</td><td>218</td></tr>
                <tr><td>3</td><td>'e-m-p-t-y'</td><td></td></tr>
                <tr><td>4</td><td>'e-m-p-t-y'</td><td></td></tr>
                <tr><td>5</td><td>'e-m-p-t-y'</td><td></td></tr>
                <tr><td>6</td><td>'e-m-p-t-y'</td><td></td></tr>
                <tr><td>7</td><td>'e-m-p-t-y'</td><td></td></tr>
                <tr><td>8</td><td>'e-m-p-t-y'</td><td></td></tr>
                <tr><td>9</td><td>'e-m-p-t-y'</td><td></td></tr>
                <tr><td>10</td><td>'e-m-p-t-y'</td><td></td></tr>
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
