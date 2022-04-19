package com.lab4.demo.book;

import com.lab4.demo.book.model.Book;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookRequestDTO;
import com.lab4.demo.book.model.dto.BookResponseDTO;
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

    public BookResponseDTO create(BookRequestDTO bookRequestDTO){
        Book book = Book.builder()
                .title(bookRequestDTO.getTitle())
                .author(bookRequestDTO.getAuthor())
                .genre(bookRequestDTO.getGenre())
                .quantity(bookRequestDTO.getQuantity())
                .price(bookRequestDTO.getPrice())
                .build();
        final Book save = bookRepository.save(book);
        return BookResponseDTO.builder()
                .id(save.getId())
                .title(save.getTitle())
                .author(save.getAuthor())
                .genre(save.getGenre())
                .quantity(save.getQuantity())
                .price(save.getPrice())
                .build();

    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public BookResponseDTO update(Long id, BookRequestDTO bookRequestDTO){
        if(!bookRepository.existsById(id)){
            return create(bookRequestDTO);
        }
        final Book book = findById(id);
        book.update(bookRequestDTO);

        final Book save = bookRepository.save(book);
        return BookResponseDTO.builder()
                .id(save.getId())
                .title(save.getTitle())
                .author(save.getAuthor())
                .genre(save.getGenre())
                .quantity(save.getQuantity())
                .price(save.getPrice())
                .build();

    }

    public BookResponseDTO rename(Long id, String newTitle){
        final Book book = findById(id);
        book.setTitle(newTitle);
        final Book save = bookRepository.save(book);
        return BookResponseDTO.builder()
                .id(save.getId())
                .title(save.getTitle())
                .author(save.getAuthor())
                .genre(save.getGenre())
                .quantity(save.getQuantity())
                .price(save.getPrice())
                .build();
    }

}
