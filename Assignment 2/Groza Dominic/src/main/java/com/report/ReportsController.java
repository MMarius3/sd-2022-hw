package com.report;

import com.book.model.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.UrlMapping.REPORT;

@RestController
@RequestMapping(REPORT)
@RequiredArgsConstructor
public class ReportsController {

    private final ReportServiceFactory reportServiceFactory;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> generateReport(@PathVariable String type) throws IOException {
        ReportType reportType = ReportType.valueOf(type.toUpperCase());
        ReportService service = reportServiceFactory.getReportService(reportType);
        return service.export();
    }

}
