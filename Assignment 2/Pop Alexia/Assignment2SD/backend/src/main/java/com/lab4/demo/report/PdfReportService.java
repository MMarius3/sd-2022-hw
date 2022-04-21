package com.lab4.demo.report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {
    @Override
    public String export(List<String> content) {
        try (PDDocument doc = new PDDocument()) {

            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                cont.beginText();
                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.setLeading(14.5f);
                cont.newLineAtOffset(25, 700);

                cont.showText("Books out of stock");
                cont.newLine();
                cont.showText("Id  Title  Author Genre Quantity Price");
                cont.newLine();

                for(String line:content){
                    cont.showText(line);
                    cont.newLine();
                }
                cont.endText();
            }
            doc.save("src/report.pdf");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

        return "I am a PDF reporter";
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
