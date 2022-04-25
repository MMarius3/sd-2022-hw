package com.lab4.demo.user;

import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.user.dto.UserDto;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> allRegularUsers(){
        return userService.allRegularUsers();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto create(@RequestBody UserDto user) {
        return userService.addUser(user);
    }

    @PatchMapping(USERS_ID_PART)
    public UserDto edit(@PathVariable Long id, @RequestBody UserDto user) {
        return userService.edit(id, user);
    }

    @DeleteMapping(DELETE)
    public void delete(@PathVariable Long id) {

        userService.delete(id);

    }
}
