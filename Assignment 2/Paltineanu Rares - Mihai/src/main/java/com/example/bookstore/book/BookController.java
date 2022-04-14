package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.bookstore.UrlMapping.BOOKS;


@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    //private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<Book> allItems() {
        return bookService.findAll();
    }

//    @GetMapping(EXPORT_REPORT)
//    public String exportReport(@PathVariable ReportType type) {
//        return reportServiceFactory.getReportService(type).export();
//    }
}
