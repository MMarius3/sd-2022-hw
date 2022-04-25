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

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO bookDTO) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(bookDTO)
        ));
    }

    public BookDTO edit(Long id, BookDTO bookDTO) {
        Book actBook = findById(id);
        actBook.setTitle(bookDTO.getTitle());
        actBook.setAuthor(bookDTO.getAuthor());
        actBook.setGenre(bookDTO.getGenre());
        actBook.setQuantity(bookDTO.getQuantity());
        actBook.setPrice(bookDTO.getPrice());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public void deleteByID(Long id){
        bookRepository.deleteById(id);
    }

    public List<BookDTO> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title,String author,String genre){
        PageRequest pageRequest = PageRequest.of(0,10);
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(title,author,genre,pageRequest).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO sell(Long id, BookDTO bookDTO){
        if(bookDTO.getQuantity() > 0){
            bookDTO.setQuantity(bookDTO.getQuantity() - 1);
        }
        return edit(id,bookDTO);
    }

    public String export(ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
