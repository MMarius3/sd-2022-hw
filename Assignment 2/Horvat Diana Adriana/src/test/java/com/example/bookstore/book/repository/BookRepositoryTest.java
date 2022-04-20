package com.example.bookstore.book.repository;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void findById(){
        final Book book = Book.builder().title("자바의 정석").author("정석").price(10000).quantity(2).build();

        final Book savedBook = bookRepository.save(book);

        final Optional<Book> optionalBook = bookRepository.findById(savedBook.getId());
        assertTrue(optionalBook.isPresent());
        final Book book1 = optionalBook.get();
        assertEquals(book1.getTitle(), "자바의 정석");
    }

}