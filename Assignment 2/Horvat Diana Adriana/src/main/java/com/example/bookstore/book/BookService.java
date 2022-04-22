package com.example.bookstore.book;

import com.example.bookstore.book.BookRepository;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public BookDTO changePrice(Long id, Double newPrice) {
        Book book = findById(id);
        book.setPrice(newPrice);
        return bookMapper.toDto(
                bookRepository.save(book)
        );
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
