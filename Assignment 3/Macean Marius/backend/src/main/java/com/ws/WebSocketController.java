package com.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @PostMapping("/notification")
    public void sendNotification() {
        webSocketService.notifyFrontend("From BackEnd WS Controller!!");
    }
}
