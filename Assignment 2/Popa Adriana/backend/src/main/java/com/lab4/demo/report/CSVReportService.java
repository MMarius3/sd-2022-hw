package com.lab4.demo.report;

import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static com.lab4.demo.report.ReportType.CSV;

@RequiredArgsConstructor
@Service
public class CSVReportService implements ReportService {

    private final BookRepository bookRepository;

    @Override
    public String export() {
        try{
            List<Book> books =  bookRepository.findAll().stream()
                    .filter(book -> book.getQuantity() == 0)
                    .collect(Collectors.toList());

            PrintWriter printWriter = new PrintWriter("OutOfStock.csv");

            for(Book book: books){
                String string = book.getId().toString()
                        + book.getTitle()
                        + book.getAuthor()
                        + book.getGenre()
                        + book.getQuantity()
                        + book.getPrice() + "\n";

                printWriter.write(string);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
