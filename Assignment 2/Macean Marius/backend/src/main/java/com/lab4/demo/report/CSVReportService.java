package com.lab4.demo.report;

import com.lab4.demo.book.BookMapper;
import com.lab4.demo.book.BookService;
import com.lab4.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import static com.lab4.demo.report.ReportType.CSV;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        Random rand = new Random();
        int rand_csv = rand.nextInt(100);
        String fileName = "books" + rand_csv + ".csv";
        List<BookDTO> books = bookService.findAll();
        try (PrintWriter out = new PrintWriter(fileName)) {
            for (BookDTO book : books) {
                out.write("Title,");
                out.write("Author,");
                out.write("Genre,");
                out.write("Price,");
                if (book.getQuantity() == 0) {
                    out.write(book.getTitle() + ",");
                    out.write(book.getAuthor() + ",");
                    out.write(book.getGenre() + ",");
                    out.write(book.getPrice() + ",");
                }
                out.close();
            }
        } catch (FileNotFoundException e) {
            return "Error creating/ writing to file.";
        }
        return "Success writing to CSV";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
