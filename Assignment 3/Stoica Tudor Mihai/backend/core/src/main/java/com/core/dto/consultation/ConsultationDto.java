package com.core.dto.consultation;

import com.core.dto.patient.PatientDto;
import com.core.dto.user.UserDto;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ConsultationDto {

    @NotNull
    private int id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private UserDto doctor;

    @NotNull
    private PatientDto patient;

    @Size(min = 10, max = 1000)
    private String details;

    public int getId() {
        return id;
    }

    public ConsultationDto setId(int id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ConsultationDto setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public UserDto getDoctor() {
        return doctor;
    }

    public ConsultationDto setDoctor(UserDto doctor) {
        this.doctor = doctor;
        return this;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public ConsultationDto setPatient(PatientDto patient) {
        this.patient = patient;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ConsultationDto setDetails(String details) {
        this.details = details;
        return this;
    }
}
