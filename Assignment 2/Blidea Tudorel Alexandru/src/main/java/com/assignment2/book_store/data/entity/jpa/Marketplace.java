package com.assignment2.book_store.data.entity.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "marketplace")
public class Marketplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quality_id")
    private Quality quality;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private User admin;
    @Column(nullable = false)
    private Float price;

}
