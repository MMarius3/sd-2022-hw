package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ReportServiceFactory reportServiceFactory;

    public BookDTO findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id)));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getQuantity() > 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findOutOfStock(){
        return bookRepository.findAll().stream()
                .filter(book -> book.getQuantity() == 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO findByTitle(String title) {
        return bookMapper.toDto(bookRepository.findByTitle(title));
    }

    public List<BookDTO> filterBooks(String filter){
        PageRequest pageRequest = PageRequest.of(0, 10);
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%"+filter+"%","%"+filter+"%","%"+filter+"%",pageRequest).stream()
                .filter(book -> book.getQuantity() > 0)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)));
    }

    public BookDTO edit(Long id,BookDTO book) {
        Book actBook = bookRepository.findById(id).get();
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());

        return bookMapper.toDto(bookRepository.save(actBook));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public String export(ReportType type) {
        return reportServiceFactory.getReportService(type).export(findOutOfStock().stream()
                .map(BookDTO::toString).collect(Collectors.toList()));
    }
}
