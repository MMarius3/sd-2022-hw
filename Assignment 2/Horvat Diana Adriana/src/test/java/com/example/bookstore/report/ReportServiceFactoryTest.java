package com.example.bookstore.report;

import com.example.bookstore.book.BookController;
import com.example.bookstore.book.BookService;
import com.example.bookstore.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.bookstore.UrlMapping.*;
import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.*;
import com.example.bookstore.BaseControllerTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ReportServiceFactoryTest extends BaseControllerTest{

    @Autowired
    @Mock
    private ReportServiceFactory reportServiceFactory;

    @InjectMocks
    private ReportController controller;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new ReportController(reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getReportService() {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals(CSV, csvReportService.getType());

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals(PDF, pdfReportService.getType());
    }

//    @Test
//    void getResponseReports() throws Exception {
//        ResultActions result = performGetWithPathVariable(USERS + EXPORT_REPORT, "csv");
//        result.andExpect(status().isOk());
//    }
}