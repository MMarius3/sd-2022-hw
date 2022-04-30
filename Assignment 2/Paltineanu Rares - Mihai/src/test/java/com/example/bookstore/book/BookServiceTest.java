package com.example.bookstore.book;

import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.mapper.BookMapper;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

public class BookServiceTest {

    @InjectMocks
    private BookService itemService;

    @Mock
    private BookRepository itemRepository;

    @Mock
    private BookMapper itemMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemService = new BookService(itemRepository, itemMapper, reportServiceFactory);
    }

    @Test
    void findAll() {
        List<Book> items = TestCreationFactory.listOf(Book.class);
        when(itemRepository.findAll()).thenReturn(items);

        List<BookDTO> all = itemService.findAll();

        Assertions.assertEquals(items.size(), all.size());
    }
}
