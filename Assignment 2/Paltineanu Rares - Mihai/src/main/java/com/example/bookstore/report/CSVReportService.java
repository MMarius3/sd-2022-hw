package com.example.bookstore.report;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSVReportService implements ReportService {


    @Override
    public void export(List<String> books) {
        try {
            PrintWriter writer = new PrintWriter("example.csv");
            CSVWriter csvWriter = new CSVWriter(writer);
            String[] header = {"ID", "Name", "Description", "Author", "Genre", "Price"};
            csvWriter.writeNext(header);
            for (String str : books) {
                String[] line = str.split(",");
                csvWriter.writeNext(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while trying to write to csv");
        }
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }


}
