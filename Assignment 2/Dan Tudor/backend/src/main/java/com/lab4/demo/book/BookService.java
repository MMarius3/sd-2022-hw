package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public List<BookDTO> findAll(){
        return bookRepository.findAllByOrderByTitle().map(BookDTO::toDTO).collect(toList());
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
