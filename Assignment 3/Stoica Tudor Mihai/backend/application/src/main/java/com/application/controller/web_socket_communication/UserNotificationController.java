package com.application.controller.web_socket_communication;

import com.core.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import javax.swing.*;

import java.util.Objects;

import static com.infrastructure.entity.UserType.DOCTOR;

@Controller
public class UserNotificationController {

    private final UserService userService;

    public UserNotificationController(UserService userService) {
        this.userService = userService;
    }

    @MessageMapping(UrlMappings.DOCTOR_CONTROLLER_NOTIFICATION_ENDPOINT)
    public void notify(String message, int userId) {

        if (Objects.requireNonNull(userService.getById(userId).orElse(null)).getUserType() == DOCTOR) {
            JOptionPane.showMessageDialog(null, message);
        }
    }
}