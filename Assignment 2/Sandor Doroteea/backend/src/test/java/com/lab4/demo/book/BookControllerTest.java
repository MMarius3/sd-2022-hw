package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.security.dto.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
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
    void allBooks() throws Exception {
        List<BookDTO> items = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(items);

        ResultActions response = performGet(BOOKS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void sellBook() throws Exception {
        BookDTO book = BookDTO.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();
        when(bookService.create(book)).thenReturn(book);
        when(bookService.sellBook(book.getId(), book)).thenReturn(1);
        ResultActions result = performPutWithRequestBodyAndPathVariables(BOOKS+ SELL +BOOKS_ID_PART,book,book.getId());
        result.andExpect(status().isOk());
    }

    @Test
    void findBooksByFilter() throws Exception {
        ArrayList<BookDTO> items = new ArrayList<>();
        BookDTO book1 = BookDTO.builder()
                .id(1L)
                .title("Felicia")
                .author("Delicia")
                .genre("horror")
                .quantity(21)
                .price(12.32)
                .build();
        BookDTO book = BookDTO.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();
        items.add(book1);
        items.add(book);
        when(bookService.create(book)).thenReturn(book);
        when(bookService.create(book1)).thenReturn(book1);
        when(bookService.findAllByFilter("%EX%")).thenReturn(items);

        ResultActions result1 = performPostWithRequestBody(BOOKS, book);
        result1.andExpect(status().isOk());
        ResultActions result2 = performPostWithRequestBody(BOOKS, book1);
        result2.andExpect(status().isOk());

        ResultActions response = performGetWithPathVariable(BOOKS+ "/filter/{filter}","%EX%");
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));
    }

    @Test
    void exportReport() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(0)
                .price(12.12)
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);
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
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(BOOKS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book created")));
        }

    @Test
    void delete() throws Exception {
        BookDTO reqItem = BookDTO.builder()
                .id(1L)
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .price(12.12)
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);
        doNothing().when(bookService).delete(reqItem.getId());
        ResultActions response = performDeleteWithPathVariable(BOOKS + BOOKS_ID_PART, reqItem.getId());
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book deleted")));
        ;
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .title("Micul print")
                .author("EXupery")
                .genre("children")
                .quantity(12)
                .id(id)
                .price(12.12)
                .build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariables(BOOKS + BOOKS_ID_PART, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book edited")));
    }

}