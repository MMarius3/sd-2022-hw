package com.example.bookstore.report;

import com.example.bookstore.book.BookService;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.bookstore.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService{
    private final BookService bookService;

    @Override
    public ResponseEntity<byte[]> export() throws IOException {
        File csvOutputFile = new File("Books out of stock.csv");
        List<BookDTO> books = bookService.findOutOfStockBooks();
        List<String[]> csvLines = new ArrayList<>();
        for (BookDTO book : books) {
            csvLines.add(new String[] {book.getTitle()});
        }

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            csvLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/vnd.ms-excel");
        responseHeaders.add("Content-Disposition", "attachment; filename=abc.csv");

        byte[] contents = Files.readAllBytes(Paths.get(csvOutputFile.getAbsolutePath()));

        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, responseHeaders, HttpStatus.OK);
        return response;
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
