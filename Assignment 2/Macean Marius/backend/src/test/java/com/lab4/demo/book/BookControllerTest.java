package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedInt(200))
                .quantity(randomBoundedInt(200))
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookService).delete(35L);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + ENTITY, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }
}