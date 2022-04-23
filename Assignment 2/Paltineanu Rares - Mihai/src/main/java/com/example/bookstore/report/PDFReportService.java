package com.example.bookstore.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class PDFReportService implements ReportService{

    @Override
    public void export(List<String> books) {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
            document.open();

            document.add(new Paragraph("ID Name Description Author Genre Price"));
            for(String str: books) {
                document.add(new Paragraph(str));
            }
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
