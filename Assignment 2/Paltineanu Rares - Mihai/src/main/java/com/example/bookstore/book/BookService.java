package com.example.bookstore.book;

import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setAuthor(book.getAuthor());
        actBook.setDescription(book.getDescription());
        actBook.setGenre(book.getGenre());
        actBook.setName(book.getName());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(bookRepository.save(actBook));

    }
}
