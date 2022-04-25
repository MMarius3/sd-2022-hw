package com.lab4.demo.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String  title;

    @Column(length = 512, nullable = false)
    private String genre;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 15, nullable = false)
    private Integer quantity;

    @Column(length = 15, nullable = false)
    private Double price;

}
