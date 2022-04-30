package com.user;

import com.book.model.dto.BookDto;
import com.security.dto.MessageResponse;
import com.user.dto.UserListDto;
import com.user.dto.UserMinimalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.UrlMapping.ENTITY;
import static com.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin
    @GetMapping
    public List<UserListDto> allUsers() {
        return userService.allUsersForList();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserListDto user){
        return userService.create(user);
    }

    @CrossOrigin
    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @CrossOrigin
    @PutMapping(ENTITY)
    public UserListDto edit(@PathVariable Long id, @RequestBody UserListDto user){

        return userService.edit(id,user);
    }
}