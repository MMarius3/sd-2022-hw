package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO create(@Valid  @RequestBody UserListDTO user){return userService.create(user);}

    @PutMapping(USERS_ID_PART)
    public UserListDTO update(@PathVariable Long id,@Valid @RequestBody UserListDTO user) {
        return userService.update(id, user);
    }

    @DeleteMapping(USERS_ID_PART)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
