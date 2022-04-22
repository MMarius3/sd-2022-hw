package com.example.assignment2.book;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

//    @Autowired
//    private final BookRepository bookRepository;
//
//    public List<BookDTO> findAll(){
//        return bookRepository.findAllByAuthor()
//                .map()
//                .collect(Collectors.toList());
//    }
}
