package com.assignment2.book_store.repository.jpa;

import com.assignment2.book_store.data.entity.jpa.Quality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualityRepository extends JpaRepository<Quality, Integer> {

    boolean existsById(Integer id);

}
