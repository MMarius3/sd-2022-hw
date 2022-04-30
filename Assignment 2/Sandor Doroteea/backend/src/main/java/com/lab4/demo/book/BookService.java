package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
        actBook.setTitle(book.getTitle());
        actBook.setGenre(book.getGenre());
        actBook.setAuthor(book.getAuthor());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public String export(ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public int sellBook(Long id, BookDTO bookDTO) {
        Book book = findById(id);
        book.setQuantity(bookDTO.getQuantity() - 1);
        if (book.getQuantity() < 0) {
            return -1;
        }
        bookRepository.save(book);
        return 0;
    }

    public List<BookDTO> findAllByFilter(String filter) {
        int page = 0;
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%" + filter + "%", "%" + filter + "%", "%" + filter + "%", pageable)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
        //return books;
    }

    }