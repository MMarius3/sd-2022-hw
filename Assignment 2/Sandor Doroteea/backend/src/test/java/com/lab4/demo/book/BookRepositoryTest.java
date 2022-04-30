package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder().title("whatever").author("kelly").genre("horror").price(12.22).quantity(12).build());
        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    public void deleteBook() {
        Book book = Book.builder()
                .title("whatever")
                .author("kelly")
                .genre("horror")
                .price(12.22)
                .quantity(12)
                .build();
        repository.save(book);
        repository.delete(book);
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void editBook(){
        Book book = Book.builder()
                .title("whatever")
                .author("kelly")
                .genre("horror")
                .price(12.22)
                .quantity(12)
                .build();
        repository.save(book);
        String oldTitle = book.getTitle();
        book.setTitle("new title");
        repository.save(book);
        assertNotEquals(oldTitle, repository.findById(book.getId()).get().getTitle());
    }

    @Test
    public void create(){
        Book book = Book.builder()
                .title("whatever")
                .author("kelly")
                .genre("horror")
                .price(12.22)
                .quantity(12)
                .build();

        repository.save(book);
        assertEquals(book, repository.findById(book.getId()).get());
    }

    @Test
    void testPaginationFilter(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        books.add(Book.builder()
                .title("whatever")
                .author("kelly")
                .genre("horror")
                .price(12.22)
                .quantity(12)
                .build());
        repository.saveAll(books);
        int page = 0;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Book> filtered = repository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%eve%","%eve%","%eve%", pageable);
        assertEquals(1, filtered.getTotalElements());
    }

    }
