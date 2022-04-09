package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_consultation",
            joinColumns = @JoinColumn(name = "consultation_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_consultation",
            joinColumns = @JoinColumn(name = "consultation_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private Patient patient;

    private String details;
}
