package com.lab4.demo.report;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import static com.lab4.demo.UrlMapping.CSV;
import static com.lab4.demo.UrlMapping.PDF;
import static com.lab4.demo.UrlMapping.REPORT;
import static com.lab4.demo.UrlMapping.REPORT_LOCAL_CSV;
import static com.lab4.demo.UrlMapping.REPORT_LOCAL_PATH;
import static com.lab4.demo.UrlMapping.REPORT_LOCAL_PDF;

@RestController
@RequestMapping(REPORT)
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    private ReportServiceFactory reportServiceFactory;
    @Autowired
    private BookService bookService;

    @GetMapping(PDF)
    public ResponseEntity<Resource> getReportPDF() throws IOException {
        System.out.println("PDF");
        bookService.export(ReportType.PDF);

        File file = new File(REPORT_LOCAL_PATH + REPORT_LOCAL_PDF);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping(CSV)
    public ResponseEntity<Resource> getReportCSV() throws IOException {
        bookService.export(ReportType.CSV);

        File file = new File(REPORT_LOCAL_PATH + REPORT_LOCAL_CSV);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
