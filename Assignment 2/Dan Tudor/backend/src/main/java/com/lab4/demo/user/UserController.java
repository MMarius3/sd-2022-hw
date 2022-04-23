package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public void createUser(@RequestBody UserListDTO userListDTO) {
        userService.createUser(userListDTO);
    }

    @PutMapping(ENTITY)
    public void updateUser(@PathVariable Long id, @RequestBody UserListDTO userListDTO) {
        userService.updateUser(id, userListDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
