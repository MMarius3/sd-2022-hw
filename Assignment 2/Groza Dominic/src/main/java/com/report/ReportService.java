package com.report;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReportService {
     ResponseEntity<?> export() throws IOException;

    ReportType getType();
}