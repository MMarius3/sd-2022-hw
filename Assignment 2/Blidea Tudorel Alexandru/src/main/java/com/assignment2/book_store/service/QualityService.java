package com.assignment2.book_store.service;


import com.assignment2.book_store.data.dto.quality.QualityCreateRequestDto;
import com.assignment2.book_store.data.dto.quality.QualityPatchRequestDto;
import com.assignment2.book_store.data.dto.quality.QualityResponseDto;
import com.assignment2.book_store.data.entity.jpa.Quality;
import com.assignment2.book_store.data.map.QualityMapper;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.repository.jpa.QualityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QualityService {

    private final QualityRepository qualityRepository;
    private final QualityMapper qualityMapper;

    public List<QualityResponseDto> getQualities() {
        return qualityRepository.findAll().stream()
                .map(qualityMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public QualityResponseDto getById(Integer id) {
        return qualityRepository.findById(id)
                .map(qualityMapper::entityToDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Requested quality doesn't exist!"));
    }

    public QualityResponseDto createQuality(QualityCreateRequestDto createRequest) {
        Quality createdQuality = qualityMapper.createRequestToEntity(createRequest);
        return qualityMapper.entityToDto(qualityRepository.save(createdQuality));
    }

    public QualityResponseDto updateQuality(QualityPatchRequestDto patchRequest) {
        Quality originalQuality = qualityRepository.getById(patchRequest.getId());
        Quality updatedQuality = qualityRepository.save(qualityMapper.patchQuality(originalQuality, patchRequest));
        return qualityMapper.entityToDto(updatedQuality);
    }

    public void deleteById(Integer id) {
        qualityRepository.deleteById(id);
    }

}
