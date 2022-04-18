package controller;

import dto.UserDto;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;
import service.UserService;
//import service.UserService;

@RestController
@RequestMapping(UrlMappings.ADMINISTRATOR)
//@RequiredArgsConstructor
@CrossOrigin
public class AdministratorController {

    private final UserService userService;

    public AdministratorController(UserService userService) {
        this.userService = userService;
    }
//
//    @PostMapping(UrlMappings.CREATE_USER_ACCOUNT)
//    public String createUserAccount(@RequestBody UserDto userDto) {
//        userService.create(userDto);
//        return "User Account created";
//    }
//
//    @PostMapping(UrlMappings.UPDATE_USER_ACCOUNT)
//    public String updateUserAccount(@RequestBody UserDto userDto) {
//        userService.update(userDto);
//        return "User Account updated";
//    }
//
//    @GetMapping(UrlMappings.GET_USER_ACCOUNT)
//    public UserDto getUserAccountById(@RequestParam("id") int id) {
//        return userService
//                .getById(id)
//                .orElse(null);
//    }
//
////    @GetMapping(UrlMappings.GET_USER_ACCOUNT)
////    public UserDto getUserAccountById(@RequestParam("name") String name) {
////        return userService
////                .getUserByUsername(name)
////                .orElse(null);
////    }
}
