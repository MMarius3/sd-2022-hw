package controller;

import entity.User;
import entity.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.IUserRepository;

@RestController
@RequestMapping(UrlMappings.ADMINISTRATOR)
@RequiredArgsConstructor
@ComponentScan(basePackages = "repository")
@CrossOrigin
public class Administrator {

    @Autowired
    private final IUserRepository userRepository;

//    @GetMapping
//    public void createUserAccount(@Req) {
//        userRepository.insert();
//    }

    @GetMapping(UrlMappings.GET_USER_ACCOUNT)
    public String getUserAccount() {
        System.out.println("getUserAccount");
//        userRepository.insert(
//                new User()
//                        .setEmail("aaa@gmail.com")
//                        .setName("aaaaaa")
//                        .setUserType(UserType.ADMIN)
//                        .setPassword("aaaaaa")
//        );
        return "aaaaaaaaaaaa";
    }
//
//    @GetMapping
//    public void updateUserAccount() {
//
//    }
//
//    @GetMapping
//    public void deleteUserAccount() {
//
//    }
}
