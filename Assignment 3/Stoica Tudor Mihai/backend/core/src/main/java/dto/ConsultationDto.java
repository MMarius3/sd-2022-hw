package dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationDto {

    @NotNull
    private int id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private UserDto doctor;

    @NotNull
    private PatientDto patient;

    private String details;
}
