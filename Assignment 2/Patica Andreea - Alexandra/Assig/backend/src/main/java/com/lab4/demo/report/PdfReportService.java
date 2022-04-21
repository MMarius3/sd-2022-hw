package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.Item;
import com.lowagie.text.*;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.lab4.demo.report.ReportType.PDF;

@Service
@AllArgsConstructor
public class PdfReportService implements ReportService {
    private final ItemService itemService;


    @Override
    public byte[] export(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf" );
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Item> itemList = itemService.findByQuantity(0);

        PDDocument document = new PDDocument();

        PDPage page = new PDPage();
        document.addPage(page);

        PDDocumentInformation pdd = document.getDocumentInformation();
        pdd.setTitle("Report");
        pdd.setSubject("Report");

        writeData(document, page, itemList);

        document.save("report" +currentDateTime+".pdf");
        //document.close();

        return convertDocumentToByteArray(document);
    }

    private byte[] convertDocumentToByteArray(PDDocument document) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return inputStream.readAllBytes();
    }

    private void writeData(PDDocument document, PDPage page, List<Item> itemList) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        //Setting the leading
        contentStream.setLeading(14.5f);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 725);

        for (Item item: itemList){
            String info = "";
            info += "Title: " + item.getTitle();
            info += ", Author: " + item.getAuthor();
            info += ", Price: " + item.getPrice();

            contentStream.showText(info);
            contentStream.newLine();
        }

        //Ending the content stream
        contentStream.endText();

        contentStream.close();
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
