package com.example.assignment2.reports;

import com.example.assignment2.bookstore.BookDTO;
import com.example.assignment2.bookstore.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.assignment2.reports.ReportType.PDF;

public class ReportPdfService implements ReportRepository{
    BookRepository bookRepository;
    @Override
    public List<BookDTO> getBooks(List<BookDTO> bookList) {
        return bookRepository.findAll().stream()
                .map(BookDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void export(List<BookDTO> bookList){
        System.out.println("export");
    }

    @Override
    public ReportType getType() {
        return PDF;
    }
}
