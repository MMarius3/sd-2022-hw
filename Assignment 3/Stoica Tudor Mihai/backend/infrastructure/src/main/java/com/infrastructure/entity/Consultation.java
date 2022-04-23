package com.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "consultation")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User doctor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Patient patient;

    @Column(name = "details")
    private String details;
}
