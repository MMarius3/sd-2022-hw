package com.example.bookstore.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.bookstore.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class ReportController {
    private final ReportServiceFactory reportServiceFactory;

    @CrossOrigin
    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<?> export(@PathVariable String type) throws IOException {
//        if(type.equals("pdf")) {
            return reportServiceFactory.getReportService(ReportType.PDF).export();
//        }
//        else{
//            reportServiceFactory.getReportService(ReportType.CSV).export();
//        }

    }
}
