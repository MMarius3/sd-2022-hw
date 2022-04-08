package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByAuthor(String author);
    Optional<Book> findByGenre(String genre);
    Optional<Book> findById(Long id);
    Stream<Book> findAllByOrderByTitle();
}
