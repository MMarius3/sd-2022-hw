package com.core.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class PatientDto {

    @NotNull
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String personalNumericalCode;

    @NotNull
    private String birthDate;

    @NotNull
    private String address;
}
