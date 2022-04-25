package com.assignment2.book_store.service;

import com.assignment2.book_store.data.dto.genre.GenreCreateRequestDto;
import com.assignment2.book_store.data.dto.genre.GenrePatchRequestDto;
import com.assignment2.book_store.data.dto.genre.GenreResponseDto;
import com.assignment2.book_store.data.entity.jpa.Genre;
import com.assignment2.book_store.data.map.GenreMapper;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.repository.jpa.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<GenreResponseDto> getGenres() {
        return genreRepository.findAll().stream()
                .map(genreMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public GenreResponseDto getGenreById(Integer id) {
        return genreRepository.findById(id)
                .map(genreMapper::entityToDto)
                .orElseThrow(() -> ErrorFactory.getError(HttpStatus.BAD_REQUEST, "There is no genre with given id"));
    }

    public GenreResponseDto createGenre(GenreCreateRequestDto createRequest) {
        Genre createdGenre = genreRepository.save(genreMapper.createGenre(createRequest));
        return genreMapper.entityToDto(createdGenre);
    }

    public GenreResponseDto updateGenre(GenrePatchRequestDto patchRequest) {
        Genre originalGenre = genreRepository.getById(patchRequest.getId());
        Genre updatedGenre = genreRepository.save(genreMapper.patchGenre(patchRequest, originalGenre));
        return genreMapper.entityToDto(updatedGenre);
    }

    public void deleteById(Integer id) {
        genreRepository.deleteById(id);
    }

}
