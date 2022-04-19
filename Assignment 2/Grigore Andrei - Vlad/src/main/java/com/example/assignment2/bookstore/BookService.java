package com.example.assignment2.bookstore;

import com.example.assignment2.bookstore.mapper.BookMapper;
import com.example.assignment2.bookstore.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    @Transactional
    public List<BookDTO> findAll(){
        return bookRepository.findAll().stream()
                .map(BookDTO :: toDTO)
                .collect(Collectors.toList());
    }

    public Book findById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Book not found: "+id));
    }

    public BookDTO create(BookDTO item) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(item)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getTitle());
        actBook.setGenre(book.getGenre());
        actBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }


}
