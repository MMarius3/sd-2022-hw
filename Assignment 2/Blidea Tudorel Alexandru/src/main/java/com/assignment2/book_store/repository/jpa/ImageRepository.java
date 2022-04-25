package com.assignment2.book_store.repository.jpa;

import com.assignment2.book_store.data.entity.jpa.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
