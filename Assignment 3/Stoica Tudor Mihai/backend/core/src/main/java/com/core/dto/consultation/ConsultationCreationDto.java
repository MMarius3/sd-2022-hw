package com.core.dto.consultation;

import com.sun.istack.NotNull;

import java.time.LocalDateTime;

public class ConsultationCreationDto {

    @NotNull
    private int id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private int doctorId;

    @NotNull
    private int patientId;

    private String details;

    public int getId() {
        return id;
    }

    public ConsultationCreationDto setId(int id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ConsultationCreationDto setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public ConsultationCreationDto setDoctorId(int doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public int getPatientId() {
        return patientId;
    }

    public ConsultationCreationDto setPatientId(int patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ConsultationCreationDto setDetails(String details) {
        this.details = details;
        return this;
    }
}
