package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDto;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsers();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getName());
        }
    }

    //TODO
//    @Test
//    void allRegularUsers() throws Exception {
//        int nrUsers = 10;
//        List<User> users = new ArrayList<>();
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role(1,ERole.EMPLOYEE);
//        roles.add(role);
//        for (int i = 0; i < nrUsers; i++) {
//            User user = User.builder()
//                    .username("User " + i)
//                    .password(UUID.randomUUID().toString())
//                    .email("user" + i + "@gmail.com")
//                    .roles(roles)
//                    .build();
//            users.add(user);
//            userRepository.save(user);
//        }
//        userRepository.saveAll(users);
//
//        List<User> foundUsers = userRepository.findAllByRoleEquals(ERole.EMPLOYEE.toString());
//        for(User user:foundUsers){
//            assertTrue(user.getRoles().contains(role));
//        }
//
//    }

    @Test
    void create() throws Exception {
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = User.builder()
                .username(randomString())
                .email(email)
                .password(password)
                .build();

        UserDto userDto = UserDto.toDto(user);

        UserDto savedUserDto = userService.addUser(userDto);

        Assertions.assertEquals(savedUserDto, userDto);
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User user = User.builder()
                .username(randomString())
                .email(email)
                .password(password)
                .build();

        userRepository.save(user);

        boolean result = userService.delete(id);
        Assertions.assertEquals(result, true);

    }
}