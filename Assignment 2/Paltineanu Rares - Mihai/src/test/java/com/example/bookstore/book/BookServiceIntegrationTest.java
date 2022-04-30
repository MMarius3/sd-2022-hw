package com.example.bookstore.book;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService ;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        Book book1 = Book.builder()
                .name("cartea 1")
                .author("autorul 1")
                .description("descrierea 1")
                .genre("genul 1")
                .price(15f)
                .quantity(10L)
                .build();
        Book book2 = Book.builder()
                .name("cartea 2")
                .author("autorul 2")
                .description("descrierea 2")
                .genre("genul 2")
                .price(15f)
                .quantity(10L)
                .build();
        bookRepository.saveAll(List.of(book1, book2));

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(2, all.size());
    }
}
