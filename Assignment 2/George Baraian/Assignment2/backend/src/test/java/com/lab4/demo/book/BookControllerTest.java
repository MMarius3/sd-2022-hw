package com.lab4.demo.book;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.review.ReviewService;
import com.lab4.demo.review.model.dto.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.*;
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

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory, reviewService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity((int) randomLong())
                .price(randomLong())
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void changeTitle() throws Exception {
        long id = randomLong();
        String newTitle = randomString();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(newTitle)
                .author(randomString())
                .genre(randomString())
                .quantity((int) randomLong())
                .price(randomLong())
                .build();

        when(bookService.changeTitle(id, newTitle)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS + ENTITY, newTitle, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity((int) randomLong())
                .price(randomLong())
                .build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void getItem() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity((int) randomLong())
                .price(randomLong())
                .build();

        when(bookService.get(id)).thenReturn(reqBook);

        ResultActions result = performGetWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(BOOKS + ENTITY, id);
        verify(bookService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }

    @Test
    void reviewsForItem() throws Exception {
        long id = randomLong();
        Set<ReviewDTO> reviewDTOs = new HashSet<>(listOf(ReviewDTO.class));

        when(reviewService.getReviewsForItem(id)).thenReturn(reviewDTOs);

        ResultActions result = performGetWithPathVariable(BOOKS + ENTITY + REVIEWS, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDTOs));
    }

}