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
    void create(){
        BookDTO book = bookService.create(BookDTO.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(25.5)
                .build());

        Assertions.assertNotNull(book);
    }

    @Test
    void edit(){
        Book book = bookRepository.save(Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(25.5)
                .build());

        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(26.0)
                .build();

        BookDTO editedBook = bookService.edit(book.getId(),bookDTO);

        Assertions.assertEquals(26.0,editedBook.getPrice());
    }

    @Test
    void delete(){
        Book book = bookRepository.save(Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(25.5)
                .build());

        bookService.deleteByID(book.getId());

        List<Book> books= bookRepository.findAll();
        Assertions.assertEquals(0,books.size());
    }

    @Test
    void sell(){
        Book book = bookRepository.save(Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(25.5)
                .build());

        BookDTO bookDTO = BookDTO.builder()
                .id(book.getId())
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(25.5)
                .build();

        BookDTO editedBook = bookService.sell(book.getId(),bookDTO);

        Assertions.assertEquals(9,editedBook.getQuantity());
    }

    @Test
    void find(){
        bookRepository.save(Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(25.5)
                .build());

        List<BookDTO> bookDTO = bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike("Lala","asd","ok");

        Assertions.assertEquals(1,bookDTO.size());
    }
}
