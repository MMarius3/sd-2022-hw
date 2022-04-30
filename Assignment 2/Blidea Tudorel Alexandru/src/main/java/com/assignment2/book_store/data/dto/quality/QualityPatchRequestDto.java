package com.assignment2.book_store.data.dto.quality;

import lombok.Data;

@Data
public class QualityPatchRequestDto {

    private Integer id;
    private String quality;
    private Byte score;

}
