package com.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.report.ReportType;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.http.HttpResponse;

import static com.report.ReportType.CSV;
import static com.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ReportServiceFactoryTest {


    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() throws IOException {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals(HttpStatus.OK, csvReportService.export().getStatusCode());

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals(HttpStatus.OK, pdfReportService.export().getStatusCode());
    }
}