package com.lab4.demo.report;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.Item;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
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
    public String export(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf" );
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Item> itemList = itemService.findByQuantity(0);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Books", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, itemList);

        document.add(table);

        document.close();
        return "I am a PDF reporter.";
    }
    /*public String export() {
        List<Item> itemList = itemService.findByQuantity(0);




        return "I am a PDF reporter.";
    }*/


    @Override
    public ReportType getType() {
        return PDF;
    }


    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("User ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Full Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Roles", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<Item> itemList) {
        for (Item item : itemList) {
            table.addCell(String.valueOf(item.getId()));
            table.addCell(item.getTitle());
            table.addCell(item.getAuthor());
            table.addCell(item.getPrice().toString());
            table.addCell(item.getDescription());
        }
    }


}
