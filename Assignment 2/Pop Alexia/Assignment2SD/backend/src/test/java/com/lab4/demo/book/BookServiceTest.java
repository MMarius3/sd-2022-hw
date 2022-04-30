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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        bookService = new BookService(bookRepository, bookMapper,reportServiceFactory);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        assertEquals(books.size(), all.size());
    }

    @Test
    void create(){
        BookDTO book = BookDTO.builder()
                .id(1L)
                .title("whatever")
                .author("whatever")
                .genre("whatever")
                .quantity(1)
                .price(20.0)
                .build();
        when(bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)))).thenReturn(book);
        BookDTO createdBook = bookService.create(book);
        Assertions.assertEquals(createdBook,book);
    }

    @Test
    void edit(){
        Long id = 1L;
        Book book = Book.builder()
                .id(id)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .quantity(1)
                .price(20.0)
                .build();
        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .quantity(1)
                .price(20.0)
                .build();

        when(bookRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(book));
        when(bookMapper.toDto(bookRepository.save(bookMapper.fromDto(bookDTO)))).thenReturn(bookDTO);
        BookDTO createdBook = bookService.create(bookDTO);
        Assertions.assertEquals("Title",createdBook.getTitle());
        createdBook.setTitle("NewTitle");
        BookDTO editedBook = bookService.edit(createdBook.getId(),createdBook);
        Assertions.assertEquals("NewTitle" ,editedBook.getTitle());

    }

    @Test
    void delete(){
        Long id = 1L;
        Book book = Book.builder()
                .id(id)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(20.0)
                .quantity(12)
                .build();

        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(id)).thenReturn(java.util.Optional.of(book));
        when(bookRepository.existsById(id)).thenReturn(false);

        bookService.delete(id);
        Assertions.assertFalse(bookRepository.existsById(id));
    }
}