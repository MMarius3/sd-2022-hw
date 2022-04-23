package com.example.bookstore.report;

import com.example.bookstore.book.BookService;
import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.example.bookstore.report.ReportType.PDF;

@Service
@RequiredArgsConstructor
public class PDFReportService implements ReportService {
    private final BookService bookService;

    @Override
    public ResponseEntity<byte[]> export() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        List<BookDTO> books = bookService.findOutOfStockBooks();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();

        for (BookDTO book : books) {
            contentStream.newLineAtOffset(25, 700 + (books.indexOf(book) * 2));
            contentStream.showText(book.getTitle());
        }
        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();

        String pathFile = "C:\\FACULTATE\\SD\\sd-2022-hw\\Assignment 2\\Horvat Diana Adriana";
        byte[] contents = Files.readAllBytes(Paths.get(pathFile + "\\pdfBoxHelloWorld.pdf"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "pdfBoxHelloWorld.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}