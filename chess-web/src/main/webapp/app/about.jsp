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

<div class="container">
    <div class="page-header">
        <h1>About</h1>
    </div>

    <p>
        Chess is a two-player board game played on a chessboard, a checkered gameboard with 64 squares arranged in an
        eight-by-eight grid. Chess is played by millions of people worldwide in homes, urban parks, clubs, online,
        correspondence, and in tournaments. In recent years, chess has become part of some school curricula.
    </p>
    <p>
        Each player begins the game with 16 pieces: one king, one queen, two rooks, two knights, two bishops, and eight
        pawns. Each of the six piece types moves differently. The most powerful piece is the queen and the least
        powerful piece is the pawn. The objective is to 'checkmate' the opponent's king by placing it under an
        inescapable threat of capture. To this end, a player's pieces are used to attack and capture the opponent's
        pieces, while supporting their own. In addition to checkmate, the game can be won by voluntary resignation by
        the opponent, which typically occurs when too much material is lost, or if checkmate appears unavoidable. A game
        may also result in a draw in several ways.
    </p>
    <p>
        Chess is believed to have originated in India, some time before the 7th century; the Indian game of chaturanga
        is also the likely ancestor of xiangqi and shogi. The pieces took on their current powers in Spain in the late
        15th century; the rules were finally standardized in the 19th century. The first generally recognized World
        Chess Champion, Wilhelm Steinitz, claimed his title in 1886.
    </p>
    <p>
        The current World Champion is the Norwegian Magnus Carlsen. The World Championship is now controlled by FIDE,
        the game's international governing body. FIDE also organizes the Women's World Championship, the World Junior
        Championship, the World Senior Championship the Blitz and Rapid World Championships and the Chess Olympiad, a
        popular competition among teams from different nations. There is also a Correspondence Chess World Championship
        and a World Computer Chess Championship.
    </p>
    <p>
        Chess is a recognized sport of the International Olympic Committee; some national sporting bodies such as the
        Spanish Consejo Superior de Deportes also recognize chess as a sport.
    </p>
    <p>
        Online chess has opened amateur and professional competition to a wide and varied group of players. There are
        also many chess variants, with different rules, different pieces, and different boards.
    </p>
    <p>
        Since the second half of the 20th century, computers have been programmed to play chess with increasing success,
        to the point where the strongest home computers play chess at a higher level than the best human players. Since
        the 1990s, computer analysis has contributed significantly to chess theory, particularly in the endgame. The
        computer Deep Blue was the first machine to overcome a reigning World Chess Champion in a match when it defeated
        Garry Kasparov in 1997.
    </p>

    <p>For more information see: <a href="https://en.wikipedia.org/wiki/Chess" target="_blank">Chess (Wikipedia.com)</a></p>

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
