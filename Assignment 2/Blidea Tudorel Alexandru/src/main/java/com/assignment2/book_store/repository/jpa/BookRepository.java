package com.assignment2.book_store.repository.jpa;

import com.assignment2.book_store.data.entity.jpa.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsById(Long id);

}
