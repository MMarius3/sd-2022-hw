package com.example.bookstore.book.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 512)
    private String genre;

    @Column(nullable = false)
    @Builder.Default
    private int quantity = 0;

    @Column(nullable = false)
    private double price = 0;
}
