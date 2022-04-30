package com.lab4.demo.user;

import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.model.dto.ItemDTO;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.allUsers();
    }

    @PostMapping(USER_CREATE)
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PatchMapping(USER_EDIT)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.edit(id, userDTO);
    }

}
