package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        assertEquals(0, bookService.findAll().size());

        final String title = "The Lord of the Rings";
        final String author = "J.R.R. Tolkien";
        final Book book1 = Book.builder().title(title).author(author).build();

        bookRepository.save(book1);

        assertEquals(1, bookService.findAll().size());
    }

}