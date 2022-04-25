package com.assignment2.book_store.service;

import com.assignment2.book_store.data.dto.book.mutations.BookResponseDto;
import com.assignment2.book_store.data.dto.user.UserReportUseful;
import com.assignment2.book_store.data.dto.user.mutations.UserResponseDto;
import com.assignment2.book_store.data.map.BookMapper;
import com.assignment2.book_store.data.map.UserMapper;
import com.assignment2.book_store.data.map.UsersReportMapper;
import com.assignment2.book_store.repository.jpa.BookRepository;
import com.assignment2.book_store.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportDataService {

    private final UserRepository userRepository;
    private final UsersReportMapper usersReportMapper;
    private final BookRepository bookRepository;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public String getUsersReport() {
        return UserResponseDto.getHeaders() + "\n" + userRepository.findAll().stream()
                .map(userMapper::entityToResponseDto)
                .map(UserResponseDto::toString)
                .collect(Collectors.joining("\n"));
    }

    public String getBooksReport() {
        return BookResponseDto.getHeaders() + "\n" + bookRepository.findAll().stream()
                .map(bookMapper::entityToDto)
                .map(BookResponseDto::toString)
                .collect(Collectors.joining("\n"));
    }

}
