package com.assignment2.book_store.controller;

import com.assignment2.book_store.data.dto.genre.GenreCreateRequestDto;
import com.assignment2.book_store.data.dto.genre.GenrePatchRequestDto;
import com.assignment2.book_store.data.dto.genre.GenreResponseDto;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.assignment2.book_store.UrlMapping.GENRE;

@RestController
@RequestMapping(GENRE)
@RequiredArgsConstructor
public class GenreController {

    private GenreService genreService;

    @GetMapping
    public List<GenreResponseDto> getGenres() {
        return genreService.getGenres();
    }

    @GetMapping("/{id}")
    public GenreResponseDto getById(@PathVariable Integer id) {
        return genreService.getGenreById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public GenreResponseDto createGenre(@RequestBody @Validated GenreCreateRequestDto createRequest) {
        return genreService.createGenre(createRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GenreResponseDto updateGenre(@PathVariable Integer id, @RequestBody @Validated GenrePatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "pathVariable id and dto id doest't match");
        }
        return genreService.updateGenre(patchRequest);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GenreResponseDto patchGenre(@PathVariable Integer id, @RequestBody @Validated GenrePatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, "pathVariable id and dto id doest't match");
        }
        return genreService.updateGenre(patchRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(@PathVariable Integer id) {
        genreService.deleteById(id);
    }

}
