package com.example.bookstore.book.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class BookFilterRequestDTO {
    @Builder.Default
    private final String name = "";

    @Builder.Default
    private final String genre = "";

    @Builder.Default
    private final String author = "";
}
