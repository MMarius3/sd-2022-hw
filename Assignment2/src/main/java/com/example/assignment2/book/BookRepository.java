package com.example.assignment2.book;

import com.example.assignment2.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Stream<Book> findAllByAuthor();
}
