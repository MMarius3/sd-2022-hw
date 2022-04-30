package com.lab4.demo.report;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import static com.lab4.demo.report.ReportType.PDF;

@RequiredArgsConstructor
@Service
public class PdfReportService implements ReportService {

    private final BookRepository bookRepository;

    @Override
    public String export() {
        try {
            List<Book> books = bookRepository.findAll().stream()
                    .filter(book -> book.getQuantity() == 0)
                    .collect(Collectors.toList());

            PDDocument pdDocument = new PDDocument();
            PDPage pdPage = new PDPage();
            pdDocument.addPage(pdPage);

            PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument,pdPage);
            pdPageContentStream.setFont(PDType1Font.TIMES_ROMAN,12);
            pdPageContentStream.setLeading(12f);
            pdPageContentStream.newLineAtOffset(25,600);
            pdPageContentStream.beginText();

            for(Book book: books){
                String string = book.getId().toString()
                        + book.getTitle()
                        + book.getAuthor()
                        + book.getGenre()
                        + book.getQuantity()
                        + book.getPrice() + "\n";

                pdPageContentStream.showText(string);
                pdPageContentStream.newLine();
            }

            pdPageContentStream.endText();
            pdPageContentStream.close();
            pdDocument.save("OutOfStock.pdf");
            pdDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
