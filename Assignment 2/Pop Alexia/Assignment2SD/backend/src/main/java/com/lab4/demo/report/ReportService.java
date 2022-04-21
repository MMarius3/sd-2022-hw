package com.lab4.demo.report;

import java.util.List;

public interface ReportService {
    String export(List<String> content);

    ReportType getType();

}
