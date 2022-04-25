package com.assignment2.book_store.service;

import com.assignment2.book_store.helper.report.ReportType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;

import static com.assignment2.book_store.helper.report.ReportType.CSV;

@Service
public class CsvReportService implements ReportService {

    @Override
    public byte[] export(String content) {
        return content.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public ReportType getType() {
        return CSV;
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("output.csv", "output.csv");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }

}
