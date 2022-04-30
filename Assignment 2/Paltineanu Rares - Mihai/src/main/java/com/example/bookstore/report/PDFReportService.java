package com.example.bookstore.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFReportService implements ReportService{

    @Override
    public String export(List<String> books) {
        try (PDDocument doc = new PDDocument()) {

            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                cont.beginText();

                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.setLeading(14.5f);

                cont.newLineAtOffset(25, 700);
                for(String str: books) {
                    cont.showText(str.replace(",", " "));
                    cont.newLine();
                }

                cont.endText();
            }

            doc.save("report.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "PDF report!";
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
