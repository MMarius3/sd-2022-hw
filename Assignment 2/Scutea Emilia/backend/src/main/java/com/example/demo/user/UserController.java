package com.example.demo.user;

import com.example.demo.security.dto.MessageResponse;
import com.example.demo.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;


@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> allUsers() {
        return userService.allUsers();
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO){
        return userService.create(userDTO);
    }

    @PutMapping(USERS_ID_EDIT)
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return userService.edit(id, userDTO);
    }

    @DeleteMapping(USERS_ID_DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(userService.delete(id)){
            return ResponseEntity.ok(new MessageResponse(String.format("User with id %d successfully deleted", id)));
        }
        return ResponseEntity.badRequest().body(new MessageResponse(String.format("Error: User with id %d not found", id)));
    }
}

