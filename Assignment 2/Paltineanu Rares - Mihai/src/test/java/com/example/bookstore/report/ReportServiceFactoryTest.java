package com.example.bookstore.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("CSV report!", csvReportService.export(new ArrayList<>()));

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("PDF report!", pdfReportService.export(new ArrayList<>()));
    }
}