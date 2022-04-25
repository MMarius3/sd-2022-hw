package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.item.model.Item;
import org.hibernate.type.TrueFalseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.data.domain.Sort.Direction.ASC;

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

    @Test
    public void findByGenre(){
        Book book = Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build();
        Book savedBook = bookRepository.save(book);

        Optional<Book> optionalBook = bookRepository.findByGenre(savedBook.getGenre());
        assertTrue(optionalBook.isPresent());
        Book book1 = optionalBook.get();
        assertTrue(book1.getGenre().equals("Adventure"));
    }

    @Test
    public void search(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        books.add(Book.builder().title("Title").author("Author").genre("Adventure").price(100).quantity(10).build());
        bookRepository.saveAll(books);
        String searchString = "%thor";
        List<Book> foundBooks = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(searchString, searchString, searchString);
        //assertEquals(books.size(), all.size());
        assertEquals(false, foundBooks.isEmpty());
    }

}
