package com.report;

import com.book.BookService;
import com.book.model.dto.BookDto;
import com.storage.StorageService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {

    private final StorageService storageService;
    private final BookService bookService;
    @Autowired
    public PdfReportService(StorageService storageService, BookService bookService) {
        this.storageService = storageService;
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<Resource> export() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();

        List<BookDto> books= bookService.findAll();
        for (BookDto book : books) {
            if(book.getQuantity()==0){
                contentStream.showText(book.getTitle()+", ");
            }
        }

        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();


        Resource file= storageService.loadAsResource("pdfBoxHelloWorld.pdf");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}