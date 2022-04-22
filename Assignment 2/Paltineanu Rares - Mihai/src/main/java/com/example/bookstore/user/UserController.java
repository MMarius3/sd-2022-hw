package com.example.bookstore.user;

import com.example.bookstore.user.dto.UserDTO;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bookstore.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(GET_USERS)
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @DeleteMapping(DELETE_USER)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping(ADD_USER)
    public UserDTO create(@RequestBody UserDTO user) {
        System.out.println(user.getEmail());
        return userService.create(user);
    }

    @GetMapping(GET_USER)
    public UserDTO findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
