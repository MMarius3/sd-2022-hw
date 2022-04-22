package com.lab4.demo.item.model;

import com.lab4.demo.user.model.Role;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private String title;

    @Column(length = 512, nullable = false)
    private String author;

    @Column(length = 11, nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 1024)
    private String description;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "item_genre",
//            joinColumns = @JoinColumn(name = "item_id"),
//            inverseJoinColumns = @JoinColumn(name = "genre_id"))
//    @Builder.Default
//    private Set<Genre> genres = new HashSet<>();
}
