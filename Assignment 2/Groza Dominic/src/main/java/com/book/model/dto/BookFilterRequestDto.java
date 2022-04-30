package com.book.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class BookFilterRequestDto {
    @Builder.Default
    private final String title="";
    @Builder.Default
    private final String author="";
    @Builder.Default
    private final String genre="";
    @Builder.Default
    private final Long price=0L;

}
