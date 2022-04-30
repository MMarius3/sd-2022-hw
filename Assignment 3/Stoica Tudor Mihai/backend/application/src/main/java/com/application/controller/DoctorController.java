package com.application.controller;

import com.core.dto.consultation.ConsultationCreationDto;
import com.core.dto.consultation.ConsultationDto;
import com.core.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMappings.DOCTOR)
@RequiredArgsConstructor
@CrossOrigin
public class DoctorController {

    private final ConsultationService consultationService;

    @PostMapping(UrlMappings.CREATE_CONSULTATION)
    public void createConsultation(@RequestBody ConsultationCreationDto consultationCreationDto) {
        try {
            consultationService.create(consultationCreationDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(UrlMappings.GET_CONSULTATIONS_BY_PATIENT_ID)
    public ConsultationDto getConsultationsByPatientId(@RequestParam("patientId") int patientId) {
        return consultationService
                .getByPatientId(patientId)
                .orElse(null);
    }
}
