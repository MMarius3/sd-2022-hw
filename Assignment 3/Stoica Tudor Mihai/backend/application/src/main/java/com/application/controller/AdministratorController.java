package com.application.controller;

import com.core.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.core.service.UserService;

@RestController
@RequestMapping(UrlMappings.ADMINISTRATOR)
@RequiredArgsConstructor
@CrossOrigin
public class AdministratorController {

    private final UserService userService;

    @PostMapping(UrlMappings.CREATE_USER_ACCOUNT)
    public String createUserAccount(@RequestBody UserDto userDto) {
        userService.create(userDto);
        return "User Account created";
    }

    @PostMapping(UrlMappings.UPDATE_USER_ACCOUNT)
    public String updateUserAccount(@RequestBody UserDto userDto) {
        userService.update(userDto);
        return "User Account updated";
    }

    @GetMapping(UrlMappings.GET_USER_ACCOUNT_BY_ID)
    public UserDto getUserAccountById(@RequestParam("id") int id) {
        return userService
                .getById(id)
                .orElse(null);
    }

    @GetMapping(UrlMappings.GET_USER_ACCOUNT_BY_NAME)
    public UserDto getUserAccountByName(@RequestParam("name") String name) {
        return userService
                .getByName(name)
                .orElse(null);
    }
}
