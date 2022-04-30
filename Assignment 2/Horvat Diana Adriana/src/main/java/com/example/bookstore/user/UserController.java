package com.example.bookstore.user;

import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.bookstore.UrlMapping.ENTITY;
import static com.example.bookstore.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @CrossOrigin
    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserListDTO user) {
        return userService.create(user);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO user) {
        return userService.edit(id, user);
    }

}
