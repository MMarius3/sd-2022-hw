package com.example.bookstore.book;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.report.CSVReportService;
import com.example.bookstore.report.PDFReportService;
import com.example.bookstore.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.bookstore.TestCreationFactory.randomLong;
import static com.example.bookstore.TestCreationFactory.randomString;
import static com.example.bookstore.UrlMapping.EXPORT_REPORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import static com.example.bookstore.UrlMapping.GET_BOOKS;
import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

public class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService itemService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PDFReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(itemService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> items = TestCreationFactory.listOf(Book.class);
        when(itemService.findAll()).thenReturn(items);

        ResultActions response = mockMvc.perform(get(GET_BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqItem = BookDTO.builder().name(randomString())
                .description(randomString())
                .build();

        when(itemService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(GET_BOOKS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void changeName() throws Exception {
        long id = randomLong();
        String newName = randomString();
        BookDTO reqItem = BookDTO.builder()
                .id(id)
                .name(newName)
                .description(randomString())
                .build();

        when(itemService.changeName(id, newName)).thenReturn(reqItem);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(ITEMS + ENTITY, newName, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        ItemDTO reqItem = ItemDTO.builder()
                .id(id)
                .name(randomString())
                .description(randomString())
                .build();

        when(itemService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariable(ITEMS + ENTITY, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void getItem() throws Exception {
        long id = randomLong();
        ItemDTO reqItem = ItemDTO.builder()
                .id(id)
                .name(randomString())
                .description(randomString())
                .build();
        when(itemService.get(id)).thenReturn(reqItem);

        ResultActions result = performGetWithPathVariable(ITEMS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        doNothing().when(itemService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(ITEMS + ENTITY, id);
        verify(itemService, times(1)).delete(id);

        result.andExpect(status().isOk());

    }

    @Test
    void reviewsForItem() throws Exception {
        long id = randomLong();
        Set<ReviewDTO> reviewDTOs = new HashSet<>(listOf(ReviewDTO.class));

        when(reviewService.getReviewsForItem(id)).thenReturn(reviewDTOs);

        ResultActions result = performGetWithPathVariable(ITEMS + ENTITY + REVIEWS, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reviewDTOs));
    }

    @Test
    void filteredItems() throws Exception {
        String nameFilter = "name filter";
        ItemFilterRequestDto filters = ItemFilterRequestDto.builder()
                .onlyExcellent(true)
                .name(nameFilter)
                .build();

        final int sortedPage = 4;
        final int sortedPageSize = 100;
        final String sortColumn = "dateCreated";
        final PageRequest pagination = PageRequest.of(sortedPage, sortedPageSize, Sort.by(ASC, sortColumn));

        Page<ItemDTO> items = new PageImpl<>(listOf(ItemDTO.class));
        when(itemService.findAllFiltered(filters, pagination)).thenReturn(items);

        ResultActions result = performGetWithModelAttributeAndParams(ITEMS + FILTERED, Pair.of("filter", filters), pairsFromPagination(pagination));

        verify(itemService, times(1)).findAllFiltered(filters, pagination);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));
    }
}
