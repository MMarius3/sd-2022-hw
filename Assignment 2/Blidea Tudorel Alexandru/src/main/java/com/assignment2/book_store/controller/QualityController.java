package com.assignment2.book_store.controller;


import com.assignment2.book_store.data.dto.quality.QualityCreateRequestDto;
import com.assignment2.book_store.data.dto.quality.QualityPatchRequestDto;
import com.assignment2.book_store.data.dto.quality.QualityResponseDto;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.service.QualityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.assignment2.book_store.UrlMapping.QUALITY;

@RestController
@RequiredArgsConstructor
@RequestMapping(QUALITY)
public class QualityController {

    private final QualityService qualityService;

    @GetMapping
    private List<QualityResponseDto> getQualities() {
        return qualityService.getQualities();
    }

    @GetMapping("/{id}")
    private QualityResponseDto getQualityById(Integer id) {
        return qualityService.getById(id);
    }

    @PostMapping
    private QualityResponseDto qualityResponseDto(@RequestBody @Validated QualityCreateRequestDto createRequest) {
        return qualityService.createQuality(createRequest);
    }

    @PutMapping("/{id}")
    private QualityResponseDto updateQuality(@PathVariable Integer id, @RequestBody @Validated QualityPatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Id not matching");
        }
        return qualityService.updateQuality(patchRequest);
    }

    @PatchMapping("/{id}")
    private QualityResponseDto patchQuality(@PathVariable Integer id, @RequestBody @Validated QualityPatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "Id not matching");
        }
        return qualityService.updateQuality(patchRequest);
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable Integer id) {
        qualityService.deleteById(id);
    }

}
