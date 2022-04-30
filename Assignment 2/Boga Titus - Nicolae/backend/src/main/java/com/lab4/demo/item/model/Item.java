package com.lab4.demo.item.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 50)
    private String author;

    @Column(length = 100)
    private String genre;

    @Column
    private int quantity;

    @Column
    private int price;


  /*  @OneToMany(mappedBy = "item")
    @Builder.Default
    private Set<Review> reviews = new HashSet<>();*/
}
