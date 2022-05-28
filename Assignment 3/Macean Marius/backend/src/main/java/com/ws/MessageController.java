package com.ws;

import com.UrlMapping;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@CrossOrigin
@RequestMapping(UrlMapping.API_PATH)
public class MessageController {

    @MessageMapping("/notification")
    @SendTo("/topic/news")
    public ResponseMessage getMessage() throws InterruptedException {
        Thread.sleep(1000);
        return new ResponseMessage(HtmlUtils.htmlEscape("Hello from Backend"));
    }
}
