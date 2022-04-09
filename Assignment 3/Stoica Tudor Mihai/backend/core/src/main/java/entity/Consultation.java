package entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Getter
@Setter
@Accessors(chain = true)
public class Consultation {
    private int id;
    private LocalDateTime date;
    private User doctor;
    private Patient patient;
    private String details;
}
