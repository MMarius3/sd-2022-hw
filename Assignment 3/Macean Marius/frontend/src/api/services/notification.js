import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import CoinList from "@/views/CoinList";

let stompClient;

export default {
  connect() {
    alert("Connected to notifications!");
    this.socket = new SockJS("http://localhost:8088/mywebsockets");
    stompClient = new Stomp.over(this.socket);
    stompClient.connect({}, function (frame) {
      console.log("Frame: " + frame);
      stompClient.subscribe("/topic/news", function (message) {
        alert(JSON.parse(message.body).content);
        console.log(JSON.parse(message.body).content);
        CoinList.methods.showNotification();
      });
    });
  },
  showMessage(message) {
    console.log("The message is: " + message);
  },
  /*sendMessage(messageContent) {
    console.log("sending message");
    stompClient.send("/app/notification", {}, JSON.stringify(messageContent));
  },*/
};
