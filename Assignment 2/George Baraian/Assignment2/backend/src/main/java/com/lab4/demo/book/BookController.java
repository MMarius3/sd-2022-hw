package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.review.ReviewService;
import com.lab4.demo.review.model.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;
    private final ReviewService reviewService;

    @GetMapping
    public List<BookDTO> allItems() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping(ENTITY)
    public BookDTO changeName(@PathVariable Long id, @RequestBody String newTitle) {
        return bookService.changeTitle(id, newTitle);
    }

    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO item) {
        return bookService.edit(id, item);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(ENTITY + REVIEWS)
    public Set<ReviewDTO> reviewsForItem(@PathVariable Long id) {
        return reviewService.getReviewsForItem(id);
    }

    @GetMapping(ENTITY)
    public BookDTO getItem(@PathVariable Long id) {
        return bookService.get(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
