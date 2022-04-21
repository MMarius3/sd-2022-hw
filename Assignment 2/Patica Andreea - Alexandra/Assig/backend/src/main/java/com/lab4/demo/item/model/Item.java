package com.lab4.demo.item.model;

import lombok.*;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {//BOOK

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String tile;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 11, nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 1024)
    private String description;

    /*
    @OneToMany(mappedBy = "item")
    @Builder.Default
    private Set<Review> reviews = new HashSet<>();
    */
}
