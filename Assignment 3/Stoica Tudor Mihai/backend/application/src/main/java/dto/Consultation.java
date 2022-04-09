package dto;

import com.sun.istack.NotNull;
import entity.Patient;
import entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Consultation {

    @NotNull
    private int id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private User doctor;

    @NotNull
    private Patient patient;

    private String details;
}
