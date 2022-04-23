package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookRequestDTO;
import com.lab4.demo.book.model.dto.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public List<BookDTO> findAll(){
        return bookRepository.findAllByOrderByTitle().map(bookMapper::toDto).collect(toList());
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public BookDTO create(BookDTO bookDTO){
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(bookDTO)));

    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public BookDTO update(Long id, BookDTO bookDTO){
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(bookDTO)));
    }

    public BookDTO rename(Long id, String newTitle){
        Book book = findById(id);
        book.setTitle(newTitle);
        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookDTO sell(Long id, Integer quantity){
        Book book = findById(id);
        book.setQuantity(book.getQuantity() - quantity);
        return bookMapper.toDto(bookRepository.save(book));
    }

    public Page<BookDTO> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre, Pageable pageable){
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(title, author, genre, pageable).map(bookMapper::toDto);
    }

    public List<BookDTO> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre){
        return bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(title, author, genre).stream().map(bookMapper::toDto).collect(toList());
    }

}
