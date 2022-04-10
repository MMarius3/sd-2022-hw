package com.book.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512,nullable = false)
    private String genre;

    @Column(length = 512,nullable = false)
    private String author;

    @Column(length = 512,nullable = false)
    private String title;



    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
