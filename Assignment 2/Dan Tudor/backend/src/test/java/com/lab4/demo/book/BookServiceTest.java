package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAll(){
        assertEquals(0, bookService.findAll().size());

        Book book = new Book().builder().title("First Title").author("First Author").genre("Action").price(100).quantity(10).build();
        Book book2 = new Book().builder().title("Wecond Title").author("Second Author").genre("Adventure").price(150).quantity(30).build();
        Book book3 = new Book().builder().title("Third Title").author("Third Author").genre("Adventure").price(80).quantity(15).build();

        bookRepository.saveAll(List.of(book, book2, book3));
        List<BookDTO> orderedBooks = bookService.findAll();
        assertEquals("Third Title", orderedBooks.get(1).getTitle());

    }


}
