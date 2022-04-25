package com.assignment2.book_store.service;


import com.assignment2.book_store.helper.report.ReportType;
import com.assignment2.book_store.helper.report.pdf.PdfBuilderFactory;
import com.assignment2.book_store.helper.report.pdf.enums.PdfBuilderType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

import static com.assignment2.book_store.helper.ServiceHelper.*;
import static com.assignment2.book_store.helper.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {

    @Value("${bookstoreApp.report.defaultPdfBuilder}")
    private PdfBuilderType pdfBuilderType;

    @Override
    public byte[] export(String content) {
        try {
            return PdfBuilderFactory.getPdfBuilder(pdfBuilderType)
                    .addTitle("Report on " + dateToString(LocalDate.now()))
                    .setCurrentFontSize(10.0f)
                    .addParagraph(content)
                    .buildToByteArray();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public ReportType getType() {
        return PDF;
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("output.pdf", "output.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }

}
