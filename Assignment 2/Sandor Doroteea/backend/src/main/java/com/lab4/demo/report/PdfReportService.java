package com.lab4.demo.report;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.lab4.demo.report.ReportType.PDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PdfReportService implements ReportService {

    private final BookRepository bookRepository;
    @Override
    public String export() {
        List<Book> booksOutOfStock = bookRepository
                .findAll()
                .stream()
                .filter(book -> book.getQuantity() == 0)
                .collect(Collectors.toList());
        try (PDDocument doc = new PDDocument()) {
            PDPage myPage = new PDPage();
            doc.addPage(myPage);

            try (PDPageContentStream cont = new PDPageContentStream(doc, myPage)) {

                cont.beginText();

                cont.setFont(PDType1Font.TIMES_ROMAN, 12);
                cont.setLeading(14.5f);

                cont.newLineAtOffset(25, 700);

                for (Book book : booksOutOfStock) {
                    cont.showText(book.toString());
                    cont.newLine();
                }
                cont.endText();
            }

            doc.save("src/main/resources/MyPdf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I am a PDF reporter.";
    }

    @Override
    public ReportType getType() {
        return PDF;
        //return null;
    }
}
