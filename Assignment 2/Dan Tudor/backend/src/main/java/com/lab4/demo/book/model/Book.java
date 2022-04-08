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

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private String genre;
    
    @Column
    private int quantity;

    @Column
    private int price;
}
