package com.application.controller;

import com.core.dto.consultation.ConsultationCreationDto;
import com.core.dto.consultation.ConsultationDto;
import com.core.dto.consultation.ConsultationUpdateDto;
import com.core.dto.patient.PatientDto;
import com.core.service.ConsultationService;
import com.core.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMappings.SECRETARY)
@RequiredArgsConstructor
@CrossOrigin
public class SecretaryController {

    private final ConsultationService consultationService;
    private final PatientService patientService;

    @PostMapping(UrlMappings.CREATE_CONSULTATION)
    public ConsultationDto createConsultation(@RequestBody ConsultationCreationDto consultationCreationDto) {
        try {
            return consultationService.create(consultationCreationDto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(UrlMappings.GET_CONSULTATIONS_BY_PATIENT_ID)
    public ConsultationDto getConsultationsByPatientId(@RequestParam("patientId") int patientId) {
        return consultationService
                .getByPatientId(patientId)
                .orElse(null);
    }

    @PostMapping(UrlMappings.UPDATE_CONSULTATION)
    public void updateConsultation(@RequestBody ConsultationUpdateDto consultationUpdateDto) {
        try {
            consultationService.update(consultationUpdateDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(UrlMappings.DELETE_CONSULTATION)
    public void deleteConsultation(@RequestParam("consultationId") int consultationId) {
        consultationService.delete(consultationId);
    }

    @PostMapping(UrlMappings.CREATE_PATIENT)
    public void createPatient(@RequestBody PatientDto patientDto) {
        patientService.createPatient(patientDto);
    }

    @PostMapping(UrlMappings.UPDATE_PATIENT)
    public void updatePatient(@RequestBody PatientDto patientDto) {
        patientService.updatePatient(patientDto);
    }
}
