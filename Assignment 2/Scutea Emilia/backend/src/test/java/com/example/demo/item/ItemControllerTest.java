package com.example.demo.item;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.item.crud.ItemController;
import com.example.demo.item.crud.ItemService;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.randomLong;
import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.UrlMapping.*;
import static com.example.demo.report.ReportType.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemControllerTest extends BaseControllerTest {

    @InjectMocks
    private ItemController controller;

    @Mock
    private ItemService itemService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ItemController(itemService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<ItemDTO> items = TestCreationFactory.listOf(Item.class);
        when(itemService.findAll()).thenReturn(items);

        ResultActions response = performGet(ITEMS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(items));

    }

    @Test
    void exportReport() throws Exception {
        final String csv = "csv";
        final String pdf = "pdf";
        when(itemService.export(CSV)).thenReturn(csv);
        when(itemService.export(PDFBox)).thenReturn(pdf);

        ResultActions responseCsv = mockMvc.perform(get(ITEMS + EXPORT_REPORT, CSV.name()));
        ResultActions responsePdf = mockMvc.perform(get(ITEMS + EXPORT_REPORT, PDFBox.name()));

        responseCsv.andExpect(status().isOk())
                .andExpect(content().string(csv));
        responsePdf.andExpect(status().isOk())
                .andExpect(content().string(pdf));
    }

    @Test
    void create() throws Exception {
        ItemDTO reqItem = ItemDTO.builder().title(randomString())
                .author(randomString())
                .build();

        when(itemService.create(reqItem)).thenReturn(reqItem);

        ResultActions result = performPostWithRequestBody(ITEMS, reqItem);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        ItemDTO reqItem = ItemDTO.builder()
                .title(randomString())
                .author(randomString())
                .build();

        when(itemService.edit(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariables(ITEMS + ITEMS_ID_EDIT, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }


}