package com.example.assignment2.reports;

import com.example.assignment2.bookstore.BookDTO;

import java.util.List;

public interface ReportRepository {
    List<BookDTO> getBooks(List<BookDTO> bookList);
    void export(List<BookDTO> bookList);
    ReportType getType();
}
