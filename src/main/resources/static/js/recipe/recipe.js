let token = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", function () {
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);
    var header = {
        Authorization: token
    };
    stompClient.connect(header, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/recipes', function (messageOutput) {
            console.log('Received from /topic/recipes: ', JSON.parse(messageOutput.body));
        });
        // You can add more subscriptions here, e.g.:
        // stompClient.subscribe('/topic/userCount', function (messageOutput) {
        //     console.log('Received from /topic/userCount: ', JSON.parse(messageOutput.body));
        // });
    }, function (error) {
        console.error('STOMP error: ' + error);
    });

    function sendRecipeMessage() {
        var message = {content: "Test message"};
        stompClient.send("/app/recipes", {}, JSON.stringify(message));
    }

    // Test sending messages
    sendRecipeMessage();
});