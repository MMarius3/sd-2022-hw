package com.lab4.demo.book.model.dto;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class BookFilterRequestDto {
    @Builder.Default
    private String title = "";
    @Builder.Default
    private String author = "";
    @Builder.Default
    private String genre = "";
    @Builder.Default
    private Integer OnlyLargeStock = 0;

    public BookFilterRequestDto(String title, String author, String genre, Integer OnlyLargeStock) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.OnlyLargeStock = OnlyLargeStock;
    }
}
