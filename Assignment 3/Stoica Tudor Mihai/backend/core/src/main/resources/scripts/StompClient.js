import * as stomp from "/webjars/stomp-websocket/stomp.min.js";
import * as jquery from "/webjars/jquery/jquery.min.js";
import * as sockjs from "/webjars/sockjs-client/sockjs.min.js";

var stompClient = null;

function connect() {

    let socket = new sockjs.SockJS('/gs-guide-websocket');
    stompClient = stomp
        .Stomp
        .over(socket)
        .connect();
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function sendMessage(message, receiverEndPoint) {
    stompClient.send(receiverEndPoint, {}, JSON.stringify(message));
}

function notifyEndpoint(message, receiverEndPoint) {
    connect();
    sendMessage(message, receiverEndPoint);
    disconnect();
}