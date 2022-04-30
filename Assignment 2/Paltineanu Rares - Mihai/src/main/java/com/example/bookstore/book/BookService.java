package com.example.bookstore.book;

import com.example.bookstore.book.mapper.BookMapper;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.book.model.dto.BookFilterRequestDTO;
import com.example.bookstore.report.ReportServiceFactory;
import com.example.bookstore.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ReportServiceFactory reportServiceFactory;
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public BookDTO findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id)));
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = bookMapper.fromDto(findById(id));
        actBook.setAuthor(book.getAuthor());
        actBook.setDescription(book.getDescription());
        actBook.setGenre(book.getGenre());
        actBook.setName(book.getName());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(bookRepository.save(actBook));
    }

    public List<BookDTO> filterBooks(String filter) {
        return bookRepository.findAllByAuthorLikeOrNameLikeOrGenreLike("%" + filter + "%"
                , "%" + filter + "%"
                , "%" + filter + "%", PageRequest.of(0, 25))
                .stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public String exportReport(List<String> books, ReportType type) {
        return reportServiceFactory.getReportService(type).export(books);
    }
}
