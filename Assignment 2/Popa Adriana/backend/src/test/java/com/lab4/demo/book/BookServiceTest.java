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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        BookDTO bookDTO = BookDTO.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();

        Book book = Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);

        BookDTO newBookDTO = bookService.create(bookDTO);

        Assertions.assertNotNull(newBookDTO);
        Assertions.assertEquals(newBookDTO.getTitle(), book.getTitle());
    }

    @Test
    void update(){
        BookDTO bookDTO = BookDTO.builder()
                .id(26L)
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();

        Book book = Book.builder()
                .id(26L)
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.findById(bookDTO.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        BookDTO updatedBookDTO = bookService.edit(bookDTO.getId(),bookDTO);

        Assertions.assertNotNull(updatedBookDTO);
        Assertions.assertEquals(updatedBookDTO.getTitle(), bookDTO.getTitle());
    }

    @Test
    void delete(){
        BookDTO bookDTO = BookDTO.builder()
                .id(26L)
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();

        bookService.deleteByID(bookDTO.getId());
 }

    @Test
    void find(){
        List<Book> books = new ArrayList<>();
        books.add(Book.builder()
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .id(123L)
                .build());

//        PageRequest pageRequest = PageRequest.of(0,10);
//        Page<Book> bookPage = new PageImpl<>(books);

        //when(bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("Lala","sefdfgd","ergr",pageRequest)).thenReturn(bookPage);

        //List<BookDTO> foundBooks = bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike("Lala","a","b");

        //Assertions.assertNotNull(foundBooks);
    }

    @Test
    void sell(){
        BookDTO bookDTO = BookDTO.builder()
                .id(26L)
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();

        Book book = Book.builder()
                .id(26L)
                .title("Lala")
                .author("Ana")
                .genre("Romance")
                .quantity(10)
                .price(34.49)
                .build();

        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.findById(bookDTO.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        BookDTO soldBook = bookService.sell(bookDTO.getId(),bookDTO);

        Assertions.assertEquals(bookDTO.getQuantity(),soldBook.getQuantity());
    }
}
