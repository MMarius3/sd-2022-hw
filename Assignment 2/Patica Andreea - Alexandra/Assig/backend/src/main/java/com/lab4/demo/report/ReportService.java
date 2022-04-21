package com.lab4.demo.report;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
    String export(HttpServletResponse response) throws IOException;

    ReportType getType();
}
