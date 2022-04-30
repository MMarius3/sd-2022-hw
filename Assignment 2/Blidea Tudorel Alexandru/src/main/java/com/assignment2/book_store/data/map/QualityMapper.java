package com.assignment2.book_store.data.map;

import com.assignment2.book_store.data.dto.quality.QualityCreateRequestDto;
import com.assignment2.book_store.data.dto.quality.QualityPatchRequestDto;
import com.assignment2.book_store.data.dto.quality.QualityResponseDto;
import com.assignment2.book_store.data.entity.jpa.Quality;
import org.mapstruct.Mapper;

import static java.util.Optional.ofNullable;

@Mapper(componentModel = "spring")
public interface QualityMapper {

    QualityResponseDto entityToDto(Quality quality);

    Quality createRequestToEntity(QualityCreateRequestDto createRequestDto);

    default Quality patchQuality(Quality original, QualityPatchRequestDto patchRequest) {
        ofNullable(patchRequest.getQuality()).ifPresent(original::setQuality);
        ofNullable(patchRequest.getScore()).ifPresent(original::setScore);
        return original;
    }


}
