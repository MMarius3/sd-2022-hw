package com.example.bookstore.book;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.book.model.dto.BookFilterRequestDTO;
import com.example.bookstore.report.CSVReportService;
import com.example.bookstore.report.PDFReportService;
import com.example.bookstore.report.ReportServiceFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.bookstore.TestCreationFactory.*;
import static com.example.bookstore.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> items = listOf(Book.class);
        when(bookService.findAll()).thenReturn(items);

        ResultActions response = mockMvc.perform(get(BOOKS + GET_BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void exportReport() throws Exception {
        String pdfResponse = "PDF report!";
        String csvResponse = "CSV report!";

        when(controller.exportReport(CSV)).thenReturn(csvResponse);
        when(controller.exportReport(PDF)).thenReturn(pdfResponse);

        ResultActions pdfExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder().name(randomString())
                .description(randomString())
                .author(randomString())
                .genre(randomString())
                .price(10F)
                .quantity(randomLong())
                .build();

        when(bookService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(BOOKS + ADD_BOOK, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void changeName() throws Exception {
        long id = randomLong();
        String newName = randomString();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .name(newName)
                .description(randomString())
                .build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        //ResultActions result = performPatchWithRequestBodyAndPathVariable(ITEMS + ENTITY, newName, id);
        //r/esult.andExpect(status().isOk())
        //       .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        BookDTO reqItem = BookDTO.builder().name(randomString())
                .description(randomString())
                .author(randomString())
                .genre(randomString())
                .price(10F)
                .quantity(randomLong())
                .build();

        when(bookService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + UPDATE_BOOK, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void getItem() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .name(randomString())
                .description(randomString())
                .build();
        when(bookService.findById(id)).thenReturn(reqBook);

        ResultActions result = performGetWithPathVariable(BOOKS + GET_BOOK, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + DELETE_BOOK, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }

    @Test
    void filteredItems() throws Exception {
        String nameFilter = "name filter";
        BookFilterRequestDTO filters = BookFilterRequestDTO.builder()
                .name(nameFilter)
                .build();

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final String sortColumn = "dateCreated";
        final PageRequest pagination = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, sortColumn));

        Page<BookDTO> items = new PageImpl<>(listOf(BookDTO.class));
//        when(bookService.filterBooks(filters, pagination)).thenReturn(items);
//
//        ResultActions result = performGetWithModelAttributeAndParams(ITEMS + FILTERED, Pair.of("filter", filters), pairsFromPagination(pagination));
//
//        verify(bookService, times(1)).findAllFiltered(filters, pagination);
//        result.andExpect(status().isOk())
//                .andExpect(jsonContentToBe(items));
    }
}
