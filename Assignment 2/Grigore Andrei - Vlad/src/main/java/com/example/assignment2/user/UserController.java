package com.example.assignment2.user;

import com.example.assignment2.user.dto.UserDetailsImp;
import com.example.assignment2.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.cfg.AvailableSettings.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers(){
        return userService.allUsersForList();
    }
}
