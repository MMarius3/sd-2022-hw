package com.example.demo.report;

import com.example.demo.item.bookstore.BookstoreService;
import com.example.demo.item.crud.ItemMapper;
import com.example.demo.item.crud.ItemRepository;
import com.example.demo.item.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.report.ReportType.*;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private BookstoreService bookstoreService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }


    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        for(int i = 0; i < 3; i++){
            Item item = Item.builder()
                    .title("title")
                    .author("author")
                    .genre("genre")
                    .price((double)i)
                    .quantity(i)
                    .build();
            itemRepository.save(item);
        }

        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("I am a CSV reporter.", csvReportService.export());

        ReportService pdfBoxReportService = reportServiceFactory.getReportService(PDFBox);
        Assertions.assertEquals("I am a PDFBox reporter.", pdfBoxReportService.export());

        ReportService pdfJasperReportService = reportServiceFactory.getReportService(PDFJasper);
        Assertions.assertEquals("I am a Jasper reporter.", pdfJasperReportService.export());
    }
}