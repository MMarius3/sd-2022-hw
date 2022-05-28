package com.report;

import java.io.PrintWriter;

public interface ReportService {
    String export();

    ReportType getType();
}
