package com.example.bookstore.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bookstore.report.ReportType.CSV;
import static com.example.bookstore.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

//    @Test
//    void getReportService() {
//        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
//        Assertions.assertEquals("I am a CSV reporter.", csvReportService.export());
//
//        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
//        Assertions.assertEquals("I am a PDF reporter.", pdfReportService.export());
//    }
}