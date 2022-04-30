package com.lab4.demo.report;

import com.lab4.demo.book.BookService;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lab4.demo.UrlMapping.REPORT_LOCAL_PATH;
import static com.lab4.demo.UrlMapping.REPORT_LOCAL_PDF;
import static com.lab4.demo.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService {

    public static String docPath = REPORT_LOCAL_PATH + REPORT_LOCAL_PDF;

    @Override
    public String export(List<BookDTO> books) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        if(books.size() == 0) {
            //write that there are no books out of stock
            document.save(docPath);
            document.close();
            return docPath;
        }

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        System.out.println(books);
        String[][] content = new String[books.size()+1][];
        content[0] = new String[]{
                "Title",
                "Author",
                "Genre",
                "Description",
                "Price",
        };
        for (int i = 0; i < books.size(); i++) {
            content[i+1] = new String[]{
                    books.get(i).getTitle(),
                    books.get(i).getAuthor(),
                    books.get(i).getGenre() != null ? books.get(i).getGenre().toString() : null,
                    books.get(i).getDescription(),
                    books.get(i).getPrice() != null ? books.get(i).getPrice().toString() : null,
            };
        }
        drawTable(page, contentStream, content, 700, 10);
        contentStream.close();

        document.save(docPath);
        document.close();

        return docPath;
    }

    private void drawTable(PDPage page, PDPageContentStream contentStream, String[][] content,
                           float y, float margin) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = page.getMediaBox().getWidth()-(2*margin);
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth/(float)cols;
        final float cellMargin=5f;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin,nexty,margin+tableWidth,nexty);
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx,y,nextx,y-tableHeight);
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);

        float textx = margin+cellMargin;
        float texty = y-15;
        for(int i = 0; i < content.length; i++){
            for(int j = 0 ; j < content[i].length; j++){
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx,texty);
                if(text != null) {
                    contentStream.drawString(text);
                }
                contentStream.endText();
                textx += colWidth;
            }
            texty-=rowHeight;
            textx = margin+cellMargin;
        }
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
