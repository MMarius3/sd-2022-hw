package com.lab4.demo.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp="^[A-Za-z .]*$",message = "Book name must be only letters")
    @Column(length = 512, nullable = false)
    private String title;

    @NotNull
    @Pattern(regexp="^[A-Za-z .]*$",message = "Author name must be only letters")
    @Column(length = 1024)
    private String author;

    @NotNull
    @Pattern(regexp="^[A-Za-z]*$",message = "Genre name must be only letters")
    @Column(length = 1024)
    private String genre;

    @NotNull
    @Column(length = 1024)
    private Integer quantity;

    @NotNull
    @Column(length = 1024)
    private Double price;


}
