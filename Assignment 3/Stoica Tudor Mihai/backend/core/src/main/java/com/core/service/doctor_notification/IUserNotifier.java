package com.core.service.doctor_notification;

import com.core.dto.consultation.ConsultationDto;

public interface IUserNotifier {
    void sendNotification(ConsultationDto consultationDto);
}
