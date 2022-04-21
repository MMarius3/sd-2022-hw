package com.lab4.demo.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 11, nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 1024)
    private String description;
}
