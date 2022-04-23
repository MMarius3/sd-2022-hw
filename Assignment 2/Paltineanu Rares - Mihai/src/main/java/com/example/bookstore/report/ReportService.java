package com.example.bookstore.report;

import java.util.List;

public interface ReportService {

    void export(List<String> books);

    ReportType getType();
}
