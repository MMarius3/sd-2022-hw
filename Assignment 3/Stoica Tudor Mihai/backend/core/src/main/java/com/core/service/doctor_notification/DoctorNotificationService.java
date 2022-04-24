package com.core.service.doctor_notification;

import com.core.dto.consultation.ConsultationDto;
import com.core.utils.FileUtils;
import com.core.utils.ScriptUtils;
import com.infrastructure.entity.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptException;
import java.io.IOException;

@Service
public class DoctorNotificationService implements IUserNotifier {

    private final RestTemplate restTemplate;

    public DoctorNotificationService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public User getUserDetails(String name) {
        return restTemplate.getForObject("/{name}/details", User.class, name);
    }

    @Override
    public void sendNotification(ConsultationDto consultationDto) {

        String script =
                FileUtils
                        .readFileFromResources("scripts/StompClient.js")
                        .orElseThrow(() -> new RuntimeException("Script not found or file could not have been read"));

        try {
            ScriptUtils.runJavaScript(script, "notifyEndpoint", getMessage(consultationDto), "/app/hello");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getMessage(ConsultationDto consultationDto) {
        return
                "You have an appointment at date " +
                        consultationDto.getDate() +
                        "with patient " +
                        consultationDto.getPatient().getName();
    }
}
