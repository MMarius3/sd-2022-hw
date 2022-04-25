package com.assignment2.book_store.repository.jpa;

import com.assignment2.book_store.data.entity.jpa.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
