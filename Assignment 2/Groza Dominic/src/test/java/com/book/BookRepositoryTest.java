package com.book;

import com.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    void simpleFindByTitle() {
        Book book = Book.builder()
                .author("Author")
                .title("Title123")
                .genre("Comedy")
                .price(12312L)
                .build();
        bookRepository.save(book);
        List<Book> list=bookRepository.findByTitle("Title123");
        assertEquals(book.getTitle(), list.get(0).getTitle());
    }

    @Test
    void findByGenre() {
    }

    @Test
    void findByAuthor() {
    }
}