package com.example.assignment2;

import com.example.assignment2.book.BookRepository;
import com.example.assignment2.book.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findById(){
        String title = "test book";
        String author = "me";
        String genre = "test";
        Book book1 = Book.builder().title(title).author(author).genre(genre).build();

        final Book savedBook = bookRepository.save(book1);

        final Optional<Book> optionalBook = bookRepository.findById(savedBook.getId());
        assertTrue(optionalBook.isPresent());

        final Book book = optionalBook.get();
        assertEquals(title, book.getTitle());
    }
}
