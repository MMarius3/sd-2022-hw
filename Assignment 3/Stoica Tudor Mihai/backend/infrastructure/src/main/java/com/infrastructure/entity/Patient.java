package com.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name", nullable = false, length = 50)
    private String name;

    @Column(name="personal_numerical_code", nullable = false, length = 50)
    private String personalNumericalCode;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "address")
    private String address;
}
