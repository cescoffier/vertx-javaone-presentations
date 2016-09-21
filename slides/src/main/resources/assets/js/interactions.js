// HTTP Hello World
$("#http-hello-world-btn").click(function () {
    var result = $("#http-hello-world-result");
    $.get("http://localhost:8081", function (data) {
        result.html(data);
    });
});

// Chained HTTP Hello World
$("#http-chained-hello-world-btn").click(function () {
    var result = $("#http-chained-hello-world-result");
    $.get("http://localhost:8082", function (data) {
        result.html(data);
    });
});

// Chained HTTP Hello World with failure
$("#failure-chain-btn").click(function () {
    var result = $("#failure-chain-result");
    $.get("http://localhost:8082", function (data) {
        result.prepend("<p>" + data + "</p>");
    });
});

// Chained HTTP Hello World with CB
$("#failure-chain-cb-btn").click(function () {
    var result = $("#failure-chain-cb-result");
    $.get("http://localhost:8082", function (data) {
        result.prepend("<p>" + data + "</p>");
    });
});

// Chained HTTP Hello World with circuit breaker
$("#fail-over-btn").click(function () {
    var result = $("#fail-over-result");
    $.get("http://localhost:8081", function (data) {
        result.prepend("<p>" + data + "</p>");
    });
});