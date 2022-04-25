package com.lab4.demo.report;

import com.lab4.demo.book.model.dto.BookDTO;

import java.util.List;

public interface ReportService {
    String export();

    ReportType getType();
}
