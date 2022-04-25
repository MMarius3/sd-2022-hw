package com.lab4.demo.user;

import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.user.dto.UserDTO;
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


    @PatchMapping(ENTITY)
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO item) {
        return userService.edit(id, item);
    }


}
