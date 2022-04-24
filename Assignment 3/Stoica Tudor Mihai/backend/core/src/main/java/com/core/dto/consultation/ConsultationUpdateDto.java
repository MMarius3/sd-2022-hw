package com.core.dto.consultation;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class ConsultationUpdateDto {

    @NotNull
    private int id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private int doctorId;

    @NotNull
    private int patientId;

    @Size(min = 10, max = 1000)
    private String details;

    public int getId() {
        return id;
    }

    public ConsultationUpdateDto setId(int id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ConsultationUpdateDto setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public ConsultationUpdateDto setDoctorId(int doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public int getPatientId() {
        return patientId;
    }

    public ConsultationUpdateDto setPatientId(int patientId) {
        this.patientId = patientId;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ConsultationUpdateDto setDetails(String details) {
        this.details = details;
        return this;
    }
}
