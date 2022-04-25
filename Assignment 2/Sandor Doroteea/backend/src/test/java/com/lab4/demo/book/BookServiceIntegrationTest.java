package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.when;

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
    void createTest(){
        BookDTO reqItem = BookDTO.builder()
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();
        BookDTO resItem = bookService.create(reqItem);
        Assertions.assertNotNull(resItem);
        Assertions.assertEquals(reqItem.getTitle(),resItem.getTitle());
    }

    @Test
    void filter(){
        List<Book> bookList = TestCreationFactory.listOf(Book.class);
        Book book1 = Book.builder()
                .title("merry")
                .author("christams")
                .genre("yye")
                .price(12.12)
                .quantity(21)
                .build();
        Book book2 = Book.builder()
                .title("paste")
                .author("fericit")
                .genre("hajsda")
                .price(21.12)
                .quantity(21)
                .build();
        bookList.add(book1);
        bookList.add(book2);
        bookRepository.saveAll(bookList);

        List<BookDTO> all = bookService.findAllByFilter("%rry%");

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void editTest(){
        BookDTO req = BookDTO.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();
        BookDTO bookDTO =  bookService.create(req);
        bookDTO.setTitle("Mica printesa");
        BookDTO resItem = bookService.edit(bookDTO.getId(),bookDTO);
        Assertions.assertNotNull(resItem);
        Assertions.assertEquals(bookDTO.getTitle(),resItem.getTitle());
    }

    @Test
    void deleteTest(){
        BookDTO req = BookDTO.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();
        BookDTO bookDTO =  bookService.create(req);
        bookService.delete(bookDTO.getId());
        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(0, all.size());
    }


}
