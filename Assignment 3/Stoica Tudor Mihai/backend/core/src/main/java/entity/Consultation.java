package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Consultation {
    @Id
    @GeneratedValue
    private int id;
    private LocalDateTime date;
    private User doctor;
    private Patient patient;
}
