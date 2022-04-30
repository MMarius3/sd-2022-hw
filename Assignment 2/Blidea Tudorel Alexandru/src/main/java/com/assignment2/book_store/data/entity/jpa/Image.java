package com.assignment2.book_store.data.entity.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String name;

    @JoinColumn(name = "extension_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ImageExtension imageExtension;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "image")
    private Set<Book> books = new LinkedHashSet<>();

}
