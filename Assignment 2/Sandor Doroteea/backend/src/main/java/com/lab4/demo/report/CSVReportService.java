package com.lab4.demo.report;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.lab4.demo.report.ReportType.CSV;

@RequiredArgsConstructor
@Service
public class CSVReportService implements ReportService {
    private final BookRepository bookRepository;
    @SneakyThrows
    @Override
    public String export() {
        List<Book> books = bookRepository
                .findAll()
                .stream()
                .filter(book -> book.getQuantity() == 0)
                .collect(Collectors.toList());

        File file = new File("src/main/resources/MyCSV.csv");

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Title,Genre,Author,Quantity,Price");
            bufferedWriter.newLine();

            for(Book book : books) {
                bufferedWriter.write(book.getTitle() + ","
                        + book.getGenre() + ","
                        + book.getAuthor() + ","
                        + book.getQuantity().toString() + ","
                        + book.getPrice().toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
