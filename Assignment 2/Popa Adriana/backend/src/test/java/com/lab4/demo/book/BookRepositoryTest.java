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
    public void create() {
        Book bookSaved = repository.save(Book.builder()
                .title("whatever")
                .author("bla")
                .genre("fwr")
                .quantity(4).
                price(23.23).
                build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> repository.save(Book.builder().build()));
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    public void edit(){
        Book book = Book.builder()
                .id(26L)
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();
        repository.save(book);

        book.setPrice(26.26);
        Book editedBook = repository.save(book);

        assertEquals(26.26,editedBook.getPrice());
    }

    @Test
    public void delete(){
        Book book = repository.save(Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build());
        repository.deleteById(book.getId());
    }

    @Test
    public void testFind(){
        Book book = repository.save(Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(25.5)
                .build());
        repository.save(book);

        PageRequest pageRequest = PageRequest.of(0,10);
        Page<Book> books = repository.findAllByTitleLikeOrAuthorLikeOrGenreLike("Lala","bla","nana",pageRequest);

        assertEquals(1,books.getTotalElements());
    }
}
