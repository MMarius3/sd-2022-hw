package com.report;

import com.book.BookService;
import com.book.model.dto.BookDto;
import com.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.report.ReportType.CSV;

@Service
public class CsvReportService implements ReportService {
    private final BookService bookService;
    private final StorageService storageService;

    @Autowired
    public CsvReportService(BookService bookService, StorageService storageService) {
        this.bookService = bookService;
        this.storageService = storageService;
    }

    @Override
    public ResponseEntity<Resource> export() {

        List<BookDto> books= bookService.findAll();
        List<String[]> dataLines = new ArrayList<>();
        for (BookDto book : books) {
            if(book.getQuantity()==0){
                String [] data = {book.getTitle()};
                dataLines.add(data);
            }
        }
       createFile(dataLines);

        Resource file= storageService.loadAsResource("csvOutputFile.csv");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);

    }

    @Override
    public ReportType getType() {
        return CSV;
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
    private File createFile(List<String[]> dataLines) {
        File csvOutputFile = new File("csvOutputFile.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch ( FileNotFoundException e) {
            e.printStackTrace();
        }
        return csvOutputFile;
    }
}