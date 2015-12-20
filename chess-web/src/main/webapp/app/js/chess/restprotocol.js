var basicUrl = "http://localhost:8555";
var restUserPath = basicUrl + "/api/users";
var restGamePath = basicUrl + "/api/games";
var restTurnPath = basicUrl + "/api/turns";

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

function restGetUserByEmail(email, cbDone, cbFail) {
    $.ajax({
        url: restUserPath + "?email=" + encodeURIComponent(email),
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
