package com.lab4.demo.book;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(reportServiceFactory, bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create(){
        BookDTO reqItem = BookDTO.builder()
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();
        when(bookMapper.toDto(bookRepository.save(bookMapper.fromDto(reqItem)))).thenReturn(reqItem);
        when(bookService.create(reqItem)).thenReturn(reqItem);
        BookDTO resItem = bookService.create(reqItem);
        Assertions.assertEquals(reqItem, resItem);
    }

    @Test
    void sellBook(){
        BookDTO bookDTO = BookDTO.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();

        Book book = Book.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        int oldQuantity = book.getQuantity();
        bookService.sellBook(bookDTO.getId(), bookDTO);
        Assertions.assertEquals(oldQuantity-1, book.getQuantity());
    }

    @Test
    void edit(){
        BookDTO bookDTO = BookDTO.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();
        Book book = Book.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);

        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        book.setAuthor("Edited");

        BookDTO edited = bookService.edit(book.getId(),bookMapper.toDto(book));
        when(bookRepository.save(book)).thenReturn(book);
        Assertions.assertEquals(edited.getId(), book.getId());

    }

    @Test
    void delete(){
        Book book = Book.builder()
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .id(1L)
                .price(12.12)
                .build();
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.existsById(book.getId())).thenReturn(false);

        bookService.delete(book.getId());
        Assertions.assertTrue(!bookRepository.existsById(book.getId()));
    }


}
