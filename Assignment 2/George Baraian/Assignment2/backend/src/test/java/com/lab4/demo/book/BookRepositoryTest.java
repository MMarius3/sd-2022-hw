package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.review.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void beforeEach() {
        reviewRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder().title("whatever").build());

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

}
