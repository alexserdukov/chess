var basicUrl = "http://localhost:8555";
var restUserPath = basicUrl + "/api/users";
var restGamePath = basicUrl + "/api/games";
var restTurnPath = basicUrl + "/api/turns";
var restStatisticsPath = basicUrl + "/api/statistics";

// USERS

function restCreateNewUser(email, password, cbDone, cbFail) {
    $.ajax({
        url: restUserPath,
        type: "POST",
        headers: {
            "Authorization": "Basic " + btoa(email + ":" + password)
        }
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetUserById(id, cbDone, cbFail) {
    $.ajax({
        url: restUserPath + "/" + id,
        type: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}

function restUpdateUser(id, user, cbDone, cbFail) {
    $.ajax({
        url: restUserPath + "/" + id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(user)
    })
        .done(cbDone)
        .fail(cbFail);
}

// GAMES

function restCreateNewGame(userId, isWhite, gameLength, cbDone, cbFail) {
    $.ajax({
        url: restGamePath,
        type: "POST",
        headers: {
            "userId": userId,
            "isWhite": isWhite,
            "gameLength": gameLength
        }
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetGameById(id, cbDone, cbFail) {
    $.ajax({
        url: restGamePath + "/" + id,
        type: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}

function restUpdateGame(id, game, cbDone, cbFail) {
    $.ajax({
        url: restGamePath + "/" + id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(game)
    })
        .done(cbDone)
        .fail(cbFail);
}

// TURNS

function restCreateNewTurn(gameId, userId, startPosition, endPosition, fen, isGameOver, cbDone, cbFail) {
    $.ajax({
        url: restTurnPath,
        type: "POST",
        headers: {
            "gameId" : gameId,
            "userId": userId,
            "startPosition": startPosition,
            "endPosition": endPosition,
            "fen": fen,
            "isGameOver" : isGameOver
        }
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetLastTurn(gameId, cbDone, cbFail) {
    $.ajax({
        url: restTurnPath + "?gameId=" + gameId + "&lastTurn=true",
        type: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}

// STATISTICS

function restGetGlobalStatistics(cbDone, cbFail) {
    $.ajax({
        url: restStatisticsPath,
        type: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}

function restGetUserStatistics(userId, cbDone, cbFail) {
    $.ajax({
        url: restStatisticsPath + "?userId=" + userId,
        type: "GET"
    })
        .done(cbDone)
        .fail(cbFail);
}
