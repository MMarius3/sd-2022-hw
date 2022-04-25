package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
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
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book book1 = findById(id);
        book1.setTitle(book1.getTitle());
        book1.setAuthor(book1.getAuthor());
        book1.setGenre(book1.getGenre());
        book1.setQuantity(book1.getQuantity());
        book1.setPrice(book1.getPrice());
        return bookMapper.toDto(
                bookRepository.save(book1)
        );
    }

    public BookDTO changeTitle(Long id, String newTitle) {
        Book book = findById(id);
        book.setTitle(newTitle);
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
