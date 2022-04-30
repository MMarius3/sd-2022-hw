package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;

import javax.validation.ConstraintViolationException;
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
    public void testSave() {
        Book bookSaved = repository.save(Book.builder()
                .title("whatever")
                .author("whatever")
                .genre("whatever")
                .quantity(1)
                .price(20.0)
                .build());

        assertNotNull(bookSaved);
        assertEquals(1,repository.findAll().size());
        assertThrows(ConstraintViolationException.class, () -> {
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
    public void testFilterBooks(){
        Book book1 = Book.builder()
                .title("ww")
                .author("ww")
                .genre("ww")
                .price(20.0)
                .quantity(1)
                .build();

        Book book2 = Book.builder()
                .title("aa")
                .author("aa")
                .genre("aa")
                .price(20.0)
                .quantity(1)
                .build();

        repository.save(book1);
        repository.save(book2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Book> all = repository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%a%", "%a%", "%a%",pageRequest);

        assertEquals(1, all.getNumberOfElements());
    }

    @Test
    public void testFindById() {
        Book book = repository.save(Book.builder()
                .id(1L)
                .title("whatever")
                .author("whatever")
                .genre("whatever")
                .quantity(1)
                .price(20.0)
                .build());
        Book bookFound = repository.findById(book.getId()).get();
        assertEquals(book, bookFound);
    }

    @Test
    public void testUpdate(){
        Book book = repository.save(Book.builder()
                .id(1L)
                .title("whatever")
                .author("whatever")
                .genre("whatever")
                .quantity(1)
                .price(20.0)
                .build());
        book.setTitle("newtitle");
        book = repository.save(book);

        assertEquals(repository.findById(book.getId()).get().getTitle(), "newtitle");
    }

    @Test
    public void testDelete(){
        Book book = repository.save(Book.builder()
                .title("whatever")
                .author("whatever")
                .genre("whatever")
                .quantity(1)
                .price(20.0)
                .build());
        repository.delete(book);
        assertTrue(repository.findAll().isEmpty());
    }


}