package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> items = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(items);

        ResultActions response = performGet(BOOKS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void exportReport() throws Exception {
        final String csv = "csv";
        final String pdf = "pdf";
        when(bookService.export(CSV)).thenReturn(csv);
        when(bookService.export(PDF)).thenReturn(pdf);

        ResultActions responseCsv = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));
        ResultActions responsePdf = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));

        responseCsv.andExpect(status().isOk())
            .andExpect(content().string(csv));
        responsePdf.andExpect(status().isOk())
            .andExpect(content().string(pdf));
    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomBoundedInt(100))
                .price(randomBoundedInt(100)*1.0)
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(BOOKS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        BookDTO reqItem = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomBoundedInt(100))
                .price(randomBoundedInt(100)*1.0)
                .build();

        when(bookService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariables(BOOKS + BOOKS_ID_PART, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception{
        doNothing().when(bookService).deleteByID(1L);

        ResultActions resultActions = performDeleteWithPathVariable(BOOKS + BOOKS_ID_PART, 1L);

        resultActions.andExpect(status().isOk());
    }

    @Test
    void sell() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(randomBoundedInt(100))
                .price(randomBoundedInt(100)*1.0)
                .build();

        when(bookService.sell(reqItem.getId(),reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariables(BOOKS + SELL, reqItem, reqItem.getId());
        result.andExpect(status().isOk());
    }

    @Test
    void find() throws Exception {
        List<BookDTO> books = new ArrayList<>();
        books.add(BookDTO.builder()
                .id(randomLong())
                .title("Lala")
                .author(randomString())
                .genre(randomString())
                .quantity(randomBoundedInt(100))
                .price(randomBoundedInt(100)*1.0)
                .build());

        when(bookService.findAll()).thenReturn(books);
        when(bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike("Lala","abc","def")).thenReturn(books);

        List<BookDTO> foundBooks = bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike("Lala","abc","def");

        ResultActions result = performGetWithRequest(BOOKS + FIND,"Lala");
        result.andExpect(status().isOk());
    }

}
