package com.assignment2.book_store.data.entity.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "image_extensions")
public class ImageExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 8, nullable = false)
    private String extension;

}
