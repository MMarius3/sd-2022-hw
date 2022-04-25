package com.assignment2.book_store.controller;

import com.assignment2.book_store.helper.report.ReportServiceFactory;
import com.assignment2.book_store.helper.report.ReportType;
import com.assignment2.book_store.service.ReportDataService;
import com.assignment2.book_store.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.assignment2.book_store.UrlMapping.REPORT;

@RestController
@RequestMapping(REPORT)
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceFactory reportServiceFactory;
    private final ReportDataService reportDataService;

    @GetMapping("/users/{type}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getReportUsers(@PathVariable ReportType type) {
        return new ResponseEntity<>(reportServiceFactory.getReportService(type).export(reportDataService.getUsersReport()),
                reportServiceFactory.getReportService(type).getHeaders(), HttpStatus.OK);
    }

    @GetMapping("/books/{type}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getReportBooks(@PathVariable ReportType type) {
        return new ResponseEntity<>(reportServiceFactory.getReportService(type).export(reportDataService.getBooksReport()),
                reportServiceFactory.getReportService(type).getHeaders(), HttpStatus.OK);
    }


}
