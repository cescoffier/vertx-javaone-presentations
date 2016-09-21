var EventBus = require('vertx3-eventbus-client');

var eb = new EventBus('http://localhost:9000/eventbus/');

eb.onopen = function () {
    var counter = 0;
    setInterval(function () {
        eb.send("eventbus-example", {
            "message": "bonjour from node (" + counter + ")",
            "from": "node"
        });
        counter++;
    }, 2000);
};