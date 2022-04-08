package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import org.hibernate.type.TrueFalseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    @AfterEach
    public void beforeAll() {
        bookRepository.deleteAll();
    }

    @Test
    public void findByTitle(){
        Book book = Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build();
        Book savedBook = bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findByTitle(savedBook.getTitle());
        assertTrue(optionalBook.isPresent());
        Book book1 = optionalBook.get();
        assertTrue(book1.getTitle().equals("Title"));
    }

    @Test
    public void findByAuthor(){
        Book book = Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build();
        Book savedBook = bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findByAuthor(savedBook.getAuthor());
        assertTrue(optionalBook.isPresent());
        Book book1 = optionalBook.get();
        assertTrue(book1.getAuthor().equals("Author"));
    }
}
