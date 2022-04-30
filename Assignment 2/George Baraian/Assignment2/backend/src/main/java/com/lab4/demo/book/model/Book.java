package com.lab4.demo.book.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 1024)
    private String author;

    @Column(length = 512)
    private String genre;

    @Column(length = 512)
    private Integer quantity;

    @Column(length = 512)
    private Long price;
}
