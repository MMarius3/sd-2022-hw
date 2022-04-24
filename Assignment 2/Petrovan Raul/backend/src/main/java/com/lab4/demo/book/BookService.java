package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.Genre;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final ReportServiceFactory reportServiceFactory;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO item) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(item)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setAuthor(book.getAuthor());
        actBook.setTitle(book.getTitle());
        actBook.setGenre(book.getGenre());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());
        actBook.setDescription(book.getDescription());
        return bookMapper.toDto(
            bookRepository.save(actBook)
        );
    }

    public String export(ReportType type) {
        try {
            return reportServiceFactory.getReportService(type).export(findOutOfStock());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BookDTO sellOne(Long id) {
        Book actBook = findById(id);
        if(actBook.getQuantity() == 0) {
            throw new IllegalArgumentException("Book is not available");
        }
        actBook.setQuantity(actBook.getQuantity() - 1);
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public List<BookDTO> findOutOfStock() {
        System.out.println(bookRepository.findBooksByQuantityLessThanEqual(0).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList()));
        return bookRepository.findBooksByQuantityLessThanEqual(0).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDTO> filter(String filter) {
        return bookRepository.findBooksByTitleContainsIgnoreCaseOrAuthorContainingIgnoreCase(filter, filter).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}
