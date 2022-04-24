package com.lab4.demo.report;

import com.lab4.demo.book.model.dto.BookDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ReportService {
    String export(List<BookDTO> books) throws IOException;

    ReportType getType();
}
