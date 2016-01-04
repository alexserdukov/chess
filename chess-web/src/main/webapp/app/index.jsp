<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Chess</title>
    <input type="hidden" id="user-id" value="${userId}"/>
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/chessboard.css" rel="stylesheet">
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    <script src="js/chess.js"></script>
    <script src="js/chessboard.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/chess/authentication.js"></script>
    <script src="js/chess/game.js"></script>
    <script src="js/chess/restprotocol.js"></script>
</head>

<%-- MAIN MENU--%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">Chess</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/index">Game</a></li>
                <li><a href="/statistics">Statistics</a></li>
                <li><a href="/about">About</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button">${userName}<span
                        class="caret"></span></a>
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

<table class="table table-bordered">
    <tr>
        <%-- GAME MENU --%>
        <td>
            <div class="input-group-lg">

                <div class="panel-group form-inline">
                    <button id="new-game" class="btn btn-default">new game</button>
                    <div class="form-group">
                        <select id="piece-color" class="form-control" style="width: 100px">
                            <option>white</option>
                            <option>black</option>
                        </select>
                    </div>
                </div>

                <div class="panel-group form-inline">
                    <button id="join-game" class="btn btn-default">join game</button>
                    <div class="form-group">
                        <input id="join-game-id" class="form-control" placeholder="game id" style="width: 100px">
                    </div>
                </div>

                <button class="btn btn-default">end game</button>

            </div>
        </td>

        <%-- CHESS BOARD --%>
        <td>
            <table style="height: 300px">
                <tr>
                    <td>
                        <div class="container-fluid">
                            <div id="board" style="width: 600px"></div>
                        </div>
                    </td>
                    <td>
                        <div id="game-info" hidden class="list-group">
                            <a class="list-group-item">
                                <h4 class="list-group-item-heading">Game info</h4>
                                <p class="list-group-item-text">Status:<strong><span id="game-status"></span></strong></p>
                                <p class="list-group-item-text">Game ID:<strong><span id="game-id"></span></strong></p>
                                <p class="list-group-item-text">Opponent:<strong><span id="game-opponent"></span></strong></p>
                                <p class="list-group-item-text">Color:<strong><span id="game-color"></span></strong></p>
                            </a>
                        </div>
                    </td>
                </tr>
            </table>
        </td>

    </tr>
</table>

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
