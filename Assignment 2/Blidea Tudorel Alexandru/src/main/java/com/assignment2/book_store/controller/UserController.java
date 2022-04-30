package com.assignment2.book_store.controller;

import com.assignment2.book_store.data.dto.user.mutations.UserCreateRequestDto;
import com.assignment2.book_store.data.dto.user.mutations.UserPatchRequestDto;
import com.assignment2.book_store.data.dto.user.mutations.UserResponseDto;
import com.assignment2.book_store.data.dto.user.searchFilters.UserSearchFilter;
import com.assignment2.book_store.exception.ErrorFactory;
import com.assignment2.book_store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.assignment2.book_store.UrlMapping.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserResponseDto> getUsers(@ModelAttribute UserSearchFilter userSearchFilter, @PageableDefault Pageable pageable) {
        return userService.getUsers(userSearchFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDto createUser(@RequestBody @Validated UserCreateRequestDto userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserPatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, String.format("Id from pathVariable(%d) and requestBody(%d) does not match", id, patchRequest.getId()));
        }
        return userService.updateUser(patchRequest);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDto patchUser(@PathVariable Long id, @RequestBody UserPatchRequestDto patchRequest) {
        if (!Objects.equals(id, patchRequest.getId())) {
            throw ErrorFactory.getError(HttpStatus.BAD_REQUEST, String.format("Id from pathVariable(%d) and requestBody(%d) does not match", id, patchRequest.getId()));
        }
        return userService.updateUser(patchRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
