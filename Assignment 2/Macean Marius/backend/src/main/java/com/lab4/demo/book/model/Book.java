package com.lab4.demo.book.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 512)
    private String genre;

    @Min(0)
    @Max(1000)
    private Integer quantity;

    @Min(0)
    @Max(300)
    private Integer price;
}
