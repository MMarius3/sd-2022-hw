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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.UrlMapping.BOOK;
import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
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
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = performGet(BOOK);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));
    }

    @Test
    void filterBooks() throws Exception {
       BookDTO bookDTO1 = BookDTO.builder()
               .id(randomLong())
               .title("ww")
               .author("www")
               .genre("www")
               .quantity(1)
               .price(20.0)
               .build();
       BookDTO bookDTO2 = BookDTO.builder()
               .id(randomLong())
               .title("aa")
               .author("aa")
               .genre("aa")
               .quantity(2)
               .price(30.0)
               .build();

       List<BookDTO> filteredBooks = List.of(bookDTO2);

       when(bookService.create(bookDTO1)).thenReturn(bookDTO1);
       when(bookService.create(bookDTO2)).thenReturn(bookDTO2);
       when(bookService.filterBooks("%aa%")).thenReturn(filteredBooks);

       ResultActions result1 = performPostWithRequestBody(BOOK, bookDTO1);
        result1.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book created successfully")));

        ResultActions result2 = performPostWithRequestBody(BOOK, bookDTO2);
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book created successfully")));

        ResultActions result3 = performGetWithPathVariable(BOOK+"/filter/{filter}","%aa%" );
        result3.andExpect(status().isOk()).andExpect(jsonContentToBe(filteredBooks));
    }

    @Test
    void exportReport() throws Exception {
        final String csv = "csv";
        final String pdf = "pdf";
        when(controller.exportReport(CSV)).thenReturn(csv);
        when(controller.exportReport(PDF)).thenReturn(pdf);

        ResultActions responseCsv = mockMvc.perform(get(BOOK + EXPORT_REPORT, CSV.name()));
        ResultActions responsePdf = mockMvc.perform(get(BOOK + EXPORT_REPORT, PDF.name()));

        responseCsv.andExpect(status().isOk())
                .andExpect(content().string(csv));
        responsePdf.andExpect(status().isOk())
                .andExpect(content().string(pdf));
    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(1)
                .price(20.0)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOK, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book created successfully")));;
    }

    @Test
    void edit() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(1)
                .price(20.0)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);
        ResultActions result = performPostWithRequestBody(BOOK, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book created successfully")));

        reqBook.setTitle("anotherTitle");
        when(bookService.edit(reqBook.getId(),reqBook)).thenReturn(reqBook);
        when(bookService.findById(reqBook.getId())).thenReturn(reqBook);
        when(bookService.findByTitle(reqBook.getTitle())).thenReturn(reqBook);

        ResultActions result2 = performPutWithRequestBodyAndPathVariables(BOOK+"/{id}", reqBook,reqBook.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book edited successfully")));
    }

    @Test
    void delete() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(1)
                .price(20.0)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);
        ResultActions result = performPostWithRequestBody(BOOK, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("Book created successfully")));

        doNothing().when(bookService).delete(reqBook.getId());

        ResultActions result2 = performDeleteWithPathVariable(BOOK +"/{id}", reqBook.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("Book deleted successfully")));
        verify(bookService, times(1)).delete(reqBook.getId());
    }
}