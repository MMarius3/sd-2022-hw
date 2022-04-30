package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;

@SpringBootTest
class BookServiceIntegrationTest {

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
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void filter(){
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

        bookRepository.saveAll(List.of(book1,book2));

        List<BookDTO> all = bookService.filterBooks("%w%");

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void create(){
        BookDTO book = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(1)
                .price(20.0)
                .build();
        book = bookService.create(book);
        Assertions.assertTrue(bookRepository.findById(book.getId()).isPresent());
    }

    @Test
    void edit() {
        BookDTO book = BookDTO.builder()
                .id(randomLong())
                .title("firstString")
                .author(randomString())
                .genre(randomString())
                .quantity(1)
                .price(20.0)
                .build();
        book = bookService.create(book);
        book.setTitle("otherstring");
        bookService.edit(book.getId(),book);

        Assertions.assertEquals(bookRepository.findById(book.getId()).get().getTitle(), book.getTitle());

    }

    @Test
    void delete() {
        BookDTO book = BookDTO.builder()
                .title("firstString")
                .author(randomString())
                .genre(randomString())
                .quantity(1)
                .price(20.0)
                .build();
        book = bookService.create(book);
        bookService.delete(book.getId());
        Assertions.assertTrue(bookRepository.findById(book.getId()).isEmpty());
    }

}