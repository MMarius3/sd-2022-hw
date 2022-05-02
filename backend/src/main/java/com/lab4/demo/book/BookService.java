package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookFilterRequestDto;
import com.lab4.demo.book.model.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final BookValidator bookValidator;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        bookValidator.validateCreateUpdate(book);
        final List<String> errors = bookValidator.getErrors();
        if (errors.isEmpty()) {
            return bookMapper.toDto(bookRepository.save(
                    bookMapper.fromDto(book)
            ));
        } else {
            return null;
        }
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        bookValidator.validateCreateUpdate(book);
        final List<String> errors = bookValidator.getErrors();
        if (errors.isEmpty()) {
            actBook.setTitle(book.getTitle());
            actBook.setAuthor(book.getAuthor());
            actBook.setGenre(book.getGenre());
            actBook.setPrice(book.getPrice());
            actBook.setQuantity(book.getQuantity());
            return bookMapper.toDto(
                    bookRepository.save(actBook)
            );
        } else {
            return null;
        }
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        bookValidator.validateBookId(id);
        final List<String> errors = bookValidator.getErrors();
        if (errors.isEmpty()) {
            bookRepository.deleteById(id);
        }
    }

    public BookDTO sell(Long id) {
        Book actBook = findById(id);
        bookValidator.validateSell(actBook);
        final List<String> errors = bookValidator.getErrors();
        if (errors.isEmpty()) {
            actBook.setQuantity(actBook.getQuantity() - 1);
            return bookMapper.toDto(
                    bookRepository.save(actBook)
            );
        } else {
            return null;
        }
    }

    public Page<BookDTO> findAllFiltered(BookFilterRequestDto filter, Pageable pageable) {
        return bookRepository.findAll(
                BookSpecifications.specificationsFromFilter(filter), pageable
        ).map(bookMapper::toDto);
    }
}
