package com.example.bookstore.report;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

public interface ReportService {
    ResponseEntity<byte[]> export() throws IOException;

    ReportType getType();
}
