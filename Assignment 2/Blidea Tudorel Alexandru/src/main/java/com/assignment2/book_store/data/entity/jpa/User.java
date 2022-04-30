package com.assignment2.book_store.data.entity.jpa;

import com.assignment2.book_store.data.entity.jpa.enums.UserRole;
import com.assignment2.book_store.data.entity.jpa.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String firstName;
    @Column(nullable = false, length = 32)
    private String lastName;
    @Column(nullable = false, length = 64)
    private String email;
    @Column(nullable = false, length = 16, unique = true)
    private String username;
    @Column(nullable = false, length = 128)
    private String passwordHash;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus accountStatus;
    private Double accountBalance;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<Book> books = new LinkedHashSet<>();

}
