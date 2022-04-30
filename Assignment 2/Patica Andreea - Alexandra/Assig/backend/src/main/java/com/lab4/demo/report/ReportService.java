package com.lab4.demo.report;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
    byte[] export(HttpServletResponse response) throws IOException;

    ReportType getType();
}
