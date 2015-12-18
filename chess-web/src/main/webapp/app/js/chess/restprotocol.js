var restUserPath = "http://localhost:8555/api/users";

// USERS

function restAddNewUser(email, password, cbDone, cbFail) {
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
