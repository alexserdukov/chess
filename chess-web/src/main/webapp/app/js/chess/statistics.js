$(document).ready(function () {
    loadStatistics();
});

function loadStatistics() {
    loadGlobalStatistics();
    loadUserStatistics();
}

function loadGlobalStatistics() {
    var globalStatistics = $("#global-statistics");
    restGetGlobalStatistics(
        function done(data) {
            data.forEach(function (statistics) {
                var html = "<tr>" +
                    "<td>" + statistics.place + "</td>" +
                    "<td>" + statistics.user.name + "</td>";
                statistics.pairList.forEach(function (pair) {
                    html += "<td>" + pair.right + "</td>";
                });
                html += "</tr>";
                globalStatistics.append(html);
            });
        });
}

function loadUserStatistics() {
    var userId = $("#user-id").val();
    var userStatistics = $("#user-statistics");
    restGetUserById(userId,
        function done(data) {
            $("#user-name").html(data.name);
        });
    restGetUserStatistics(userId,
        function done(data) {
            data.forEach(function (statistics) {
                statistics.pairList.forEach(function (pair) {
                    userStatistics
                        .append("<tr><td>" + pair.left + "</td><td>" + pair.right + "</td></tr>")
                })
            });
        });
}
