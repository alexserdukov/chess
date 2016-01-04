var userId, gameId, board, game, orientation;

$(document).ready(function () {
    userId = $("#user-id").val();

    $("#new-game").click(function () {
        if ($("#piece-color").val() == "white") {
            createNewGame(true);
        } else {
            createNewGame(false);
        }
    });

    $("#join-game").click(function () {
        connectToGame();
    });

    setInterval(getGame, 2000);
});

function createNewGame(isWhite) {
    orientation = isWhite ? "white" : "black";
    restCreateNewGame(userId, isWhite, 3600,
        function done(data) {
            gameId = data.id;
            log("Game created successfully. Sample of data:", data);
            initBoard();
            log("Board successfully initialized!");
            $("#game-info").show();
        },
        function fail(jqXHR, textStatus, errorThrown) {
            log("Create new game error! status: ", textStatus + ", error: " + errorThrown);
        });
}

function connectToGame() {
    restGetUserById(userId,
        function done(user) {
            restGetGameById($("#join-game-id").val(),
                function done(game) {
                    setOpponent(game, user);
                },
                function fail(jqXHR, textStatus, errorThrown) {
                    log("Get game error! status: ", textStatus + ", error: " + errorThrown);
                }
            )
        },
        function fail(jqXHR, textStatus, errorThrown) {
            log("Get user by id \"" + userId + "\" error! status: ", textStatus + ", error: " + errorThrown);
        });

    var setOpponent = function (game, user) {
        if (game.white == null) {
            game.white = user;
            orientation = "white";
            updateGameData(game);
        } else if (game.black == null) {
            game.black = user;
            orientation = "black";
            updateGameData(game);
        } else if (game.white.id == userId) {
            orientation = "white";
            gameId = game.id;
            initBoard();
        } else if (game.black.id == userId) {
            orientation = "black";
            gameId = game.id;
            initBoard();
        } else {
            // game isn't available for connection
        }
    };

    var updateGameData = function (game) {
        restUpdateGame(game.id, game,
            function done() {
                gameId = game.id;
                log("Game successfully updated!");
                initBoard();
            },
            function fail(jqXHR, textStatus, errorThrown) {
                log("Game update error! status: ", textStatus + ", error: " + errorThrown);
            });
    };
}

function getGame() {
    if (gameId == null) {
        return;
    }
    restGetLastTurn(gameId,
        function done(data) {
            var lastTurn = data[0];
            game.load(lastTurn.fen);
            board.position(game.fen());
            log("Get game by id: DONE!. Sample of data:", data);
            updateGameInfo();
        },
        function fail(jqXHR, textStatus, errorThrown) {
            log("Get last turn by game id: \"" + gameId + "\" error! status: ", textStatus + ", error: " + errorThrown);
        });
}

function createNewTurn(from, to) {
    restCreateNewTurn(gameId, userId, from, to, game.fen(), game.game_over(),
        function done(data) {
            log("Turn created. Sample of data:", data);
        },
        function fail(jqXHR, textStatus, errorThrown) {
            log("Turn create error! status: ", textStatus + ", error: " + errorThrown);
        });
}

function updateGameInfo() {
    var status = '';
    var statusEl = $('#game-status');

    var moveColor = 'White';
    if (game.turn() === 'b') {
        moveColor = 'Black';
    }

    // checkmate?
    if (game.in_checkmate() === true) {
        status = 'Game over, ' + moveColor + ' is in checkmate.';
    }

    // draw?
    else if (game.in_draw() === true) {
        status = 'Game over, drawn position';
    }

    // game still on
    else {
        status = moveColor + ' to move';

        // check?
        if (game.in_check() === true) {
            status += ', ' + moveColor + ' is in check';
        }
    }

    statusEl.html(status);
    $('#game-id').html(gameId);
}

var initBoard = function () {

    var onDragStart = function (source, piece, position, orientation) {
        // do not pick up pieces if the game is over
        // only pick up pieces for the side to move
        if (game.game_over() === true ||
            (game.turn() === 'w' && piece.search(/^b/) !== -1) ||
            (game.turn() === 'b' && piece.search(/^w/) !== -1)) {
            return false;
        }

        // only allow pieces to be dragged when the board is oriented
        // in their direction
        if ((orientation === 'white' && piece.search(/^w/) === -1) ||
            (orientation === 'black' && piece.search(/^b/) === -1)) {
            return false;
        }
    };

    var onDrop = function (source, target) {
        // see if the move is legal
        var move = game.move({
            from: source,
            to: target,
            promotion: 'q' // NOTE: always promote to a queen for example simplicity
        });

        // illegal move
        if (move === null) return 'snapback';

        createNewTurn(source, target);

        updateGameInfo();
    };

    var onSnapEnd = function () {
        // update the board position after the piece snap
        // for castling, en passant, pawn promotion
        board.position(game.fen());
    };

    var cfg = {
        draggable: true,
        position: 'start',
        onDragStart: onDragStart,
        onDrop: onDrop,
        onSnapEnd: onSnapEnd,
        orientation: orientation
    };
    game = new Chess();
    board = new ChessBoard('board', cfg);
    updateGameInfo();
};


/// DEBUG
function log(message) {
    if (console && console.log) {
        console.log(message);
    }
}
