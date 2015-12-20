$(document).ready(function () {
    $("#btn-logout").click(function () {
        logout();
    });
    $("#btn-register").click(function () {
        registration();
    });
});

function logout() {
    $.ajax({
        url: "/j_spring_security_logout",
        type: "POST"
    })
        .done(function () {
            window.location = "/login";
        })
        .fail(function () {
            alert("logout error...");
        });
}

function registration() {
    var email = $("#email");
    var username = $("#username");
    var password = $("#password");
    var panelInfo = $("#panel-info");

    if (email.val() == "" || username.val() == "" || password.val() == "") {
        $("#panel-info")
            .removeClass("alert-success")
            .addClass("alert-warning")
            .html("All required fields must be filled!")
            .show();
        return;
    }

    panelInfo.hide();
    restCreateNewUser(email.val(), password.val(),
        function done(user) {
            user.name = username.val();
            restUpdateUser(user.id, user)
            panelInfo
                .removeClass("alert-warning")
                .addClass("alert-success")
                .html("User was created. Please log in!")
                .show();
        },
        function fail() {
            panelInfo
                .removeClass("alert-success")
                .addClass("alert-warning")
                .html("User with this email already exists!")
                .show();
        })
}
