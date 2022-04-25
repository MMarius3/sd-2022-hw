package com.example.demo.item;

import com.example.demo.TestCreationFactory;
import com.example.demo.item.crud.ItemMapper;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.crud.ItemService;
import com.example.demo.item.model.Item;
import com.example.demo.item.model.dto.ItemDTO;
import com.example.demo.report.ReportServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemService = new ItemService(reportServiceFactory, itemRepository, itemMapper);
    }

    @Test
    void findAll() {
        List<Item> items = TestCreationFactory.listOf(Item.class);
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemDTO> all = itemService.findAll();

        Assertions.assertEquals(items.size(), all.size());
    }
}