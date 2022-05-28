package com.report;

import org.springframework.stereotype.Service;

import java.io.PrintWriter;

import static com.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {
    @Override
    public String export() {
        return "I am a PDF reporter.";
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
