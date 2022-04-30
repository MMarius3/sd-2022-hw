package com.book;

import com.book.model.Book;
import com.book.model.dto.BookDto;
import com.book.model.dto.BookFilterRequestDto;
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

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found" + id));
    }

    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDto create(BookDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)));
    }

    public BookDto edit(Long id, BookDto book) {
        Book bookToUpdate = findById(id);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setQuantity(book.getQuantity());
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDto changeTitle(Long id, String title) {
        Book bookToUpdate = findById(id);
        bookToUpdate.setTitle(title);
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }

    public BookDto changePrice(Long id, Long price) {
        Book bookToUpdate = findById(id);
        bookToUpdate.setPrice(price);
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }

    public BookDto changeAuthor(Long id, String author) {
        Book bookToUpdate = findById(id);
        bookToUpdate.setAuthor(author);
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }

    public BookDto changeGenre(Long id, String genre) {
        Book bookToUpdate = findById(id);
        bookToUpdate.setGenre(genre);
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }

    public BookDto changeQuantity(Long id, Long quantity) {
        Book bookToUpdate = findById(id);
        bookToUpdate.setQuantity(bookToUpdate.getQuantity()-quantity);
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }

    public Page<BookDto> findAllFiltered(BookFilterRequestDto filter, Pageable pageable) {
        return bookRepository.findAll(
                BookSpecifications.specificationsFromFilter(filter), pageable
        ).map(bookMapper::toDto);
    }

    public BookDto get(Long id) {
        return bookMapper.toDto(findById(id));
    }
}
