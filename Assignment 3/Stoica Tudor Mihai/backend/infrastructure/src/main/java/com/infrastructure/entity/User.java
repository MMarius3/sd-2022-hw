package com.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name", nullable = false, length = 50)
    private String name;

    @Column(name="email", nullable = false, length = 50)
    private String email;

    @Column(name="password", nullable = false, length = 50)
    private String password;

    @Column(name = "user_type")
    private UserType userType;
}
