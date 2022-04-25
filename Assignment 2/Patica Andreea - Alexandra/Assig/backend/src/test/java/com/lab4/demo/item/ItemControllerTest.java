package com.lab4.demo.item;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.report.CSVReportService;
import com.lab4.demo.report.PdfReportService;
import com.lab4.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemControllerTest extends BaseControllerTest {

    @InjectMocks
    private ItemController controller;

    @Mock
    private ItemService itemService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ItemController(itemService,reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        when(itemService.findAll()).thenReturn(items);

        ResultActions response = performGet(ITEMS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void exportReport() throws Exception {
        final String csv = "csv";
        final String pdf = "pdf";
        //when(itemService.export(CSV)).thenReturn(csv);
        //when(itemService.export(PDF)).thenReturn(pdf);

        ResultActions responseCsv = mockMvc.perform(get(ITEMS + EXPORT_REPORT, CSV.name()));
        ResultActions responsePdf = mockMvc.perform(get(ITEMS + EXPORT_REPORT, PDF.name()));

        responseCsv.andExpect(status().isOk())
                .andExpect(content().string(csv));
        responsePdf.andExpect(status().isOk())
                .andExpect(content().string(pdf));
    }

    @Test
    void create() throws Exception {
        ItemDto reqItem = ItemDto.builder().title(randomString())
                .description(randomString())
                .author(randomString())
                .quantity((int) randomLong())
                .price((int) randomLong())
                .build();

        when(itemService.addItem(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(ITEMS, reqItem);

        verify(itemService, times(1)).addItem(reqItem);

        ResultMatcher jsonReqItem = jsonContentToBe(reqItem);
        result.andExpect(status().isCreated());
        MvcResult mvcResult = result.andReturn();
        jsonReqItem.match(mvcResult);
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        ItemDto reqItem = ItemDto.builder()
                .title(randomString())
                .author(randomString())
                .price((int)randomLong())
                .quantity((int)randomLong())
                .description(randomString())
                .build();

        when(itemService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(ITEMS + ITEMS_ID_PART, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();

        when(itemService.delete(id)).thenReturn(true);

        ResultActions result = performDeleteWIthPathVariable(ITEMS + DELETE, id);

        result.andExpect(status().isOk());

    }
}