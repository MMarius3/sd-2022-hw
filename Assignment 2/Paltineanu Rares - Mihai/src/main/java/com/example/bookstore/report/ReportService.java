package com.example.bookstore.report;

import java.util.List;

public interface ReportService {

    String export(List<String> books);

    ReportType getType();
}
