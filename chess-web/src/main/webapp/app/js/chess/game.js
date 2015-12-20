var basicGameUrl = ""; // TODO
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
        connectToGameFunc($("#join-game-id").val());
    });

    setInterval(getGame_trigger, 2000);
});

function getGame_trigger() {
    // TODO
    getGameFunc(gameId);
}

var createNewGame = function (isWhite) {

    orientation = isWhite ? "white" : "black";

    restCreateNewGame(userId, isWhite, 3600,
        function done(data) {
            gameId = data.id;
            if (console && console.log) {
                console.log("Game created successfully. Sample of data:", data);
            }
            init();
            $("#game-info").show();
        },
        function fail(jqXHR, textStatus, errorThrown) {
            if (console && console.log) {
                console.log("Create new game error! status: ", textStatus + ", error: " + errorThrown);
            }
        });
};

var connectToGameFunc = function (curGameId) {
    // TODO
    var request = $.ajax({
        url: basicGameUrl + "/connect/" + curGameId,
        method: "POST",
        data: JSON.stringify({}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false
    }).done(function (data) {
        gameId = data.gameId;
        orientation = orientation = data.isWhite ? "white" : "black";
        if (console && console.log) {
            console.log("Sample of data:", data);
        }
        init();

    });
};

var getGameFunc = function (curGameId) {
    // TODO
    if (curGameId == null) {
        return;
    }

    var request = $.ajax({
        url: basicGameUrl + "/" + curGameId,
        method: "GET",
        data: JSON.stringify({}),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false
    }).done(function (data) {
        game.load(data.fen);
        board.position(game.fen());
        //gameId = data.gameId;
        if (console && console.log) {
            console.log("Sample of data:", data);
        }
        updateStatus();

    });
};


///////////////////////////////// BOARD /////////////////////////////////
var init = function () {
    // TODO

//--- start example JS ---
    game = new Chess();


    var turnFunc = function (from, to) {
        // TODO
        var dataVal = {
            gameId: gameId,
            userId: userId,
            startPosition: from,
            endPosition: to,
            fen: game.fen(),
            gameOver: game.game_over()
        };
        var request = $.ajax({
            url: basicGameUrl + "/turn",
            method: "POST",
            data: JSON.stringify(dataVal),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false
        }).done(function (data) {
            if (console && console.log) {
                console.log("Sample of data:", data);
            }
        });
    };

    var onDragStart = function (source, piece, position, orientation) {
        // TODO
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
        // TODO
        // see if the move is legal
        var move = game.move({
            from: source,
            to: target,
            promotion: 'q' // NOTE: always promote to a queen for example simplicity
        });

        // illegal move
        if (move === null) return 'snapback';

        turnFunc(source, target);

        updateStatus();
    };

// update the board position after the piece snap
// for castling, en passant, pawn promotion
    var onSnapEnd = function () {
        // TODO
        board.position(game.fen());
    };

    var cfg = {
        pieceTheme: 'img/chesspieces/alpha/{piece}.png',
        draggable: true,
        position: 'start',
        onDragStart: onDragStart,
        onDrop: onDrop,
        onSnapEnd: onSnapEnd
        , orientation: orientation
    };
    board = new ChessBoard('board', cfg);

    updateStatus();
};

var updateStatus = function () {
    // TODO
    var status = '';
    var statusEl = $('#game-status'),
        fenEl = $('#fen'),
        pgnEl = $('#pgn');

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
    //fenEl.html(game.fen());
    //pgnEl.html(game.pgn());
    $('#game-id').html(gameId);
};
