package com.assignment2.book_store.data.entity.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String genre;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "genre")
    private Set<Book> books = new LinkedHashSet<>();

}
