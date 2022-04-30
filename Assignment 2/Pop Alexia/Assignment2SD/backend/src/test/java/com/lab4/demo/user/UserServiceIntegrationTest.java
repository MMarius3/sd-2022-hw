package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.user.model.ERole.ADMIN;
import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,EMPLOYEE));
    }

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

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }

    @Test
    void filter(){
        User user1 = User.builder()
                .id(randomLong())
                .username("wwwwwww")
                .email("email@email.com")
                .password(encodePassword("password"))
                .build();

        User user2 = User.builder()
                .id(randomLong())
                .username("qqqqqqq")
                .email("email2@email.com")
                .password(encodePassword("password"))
                .build();

        userRepository.saveAll(List.of(user1,user2));

        List<UserDTO> all = userService.filterUsers("%w%");

        Assertions.assertEquals(1, all.size());
    }

    @Test
    void create(){
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .email("email@email.com")
                .password(passwordEncoder.encode(randomString()))
                .role(EMPLOYEE.name())
                .build();
        user  = userService.create(user);
        Assertions.assertTrue(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    void edit() {
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email("email@email.com")
                .password(passwordEncoder.encode(randomString()))
                .role(EMPLOYEE.name())
                .build();
        user = userService.create(user);
        user.setUsername("otherstring");
        userService.edit(user.getId(),user);

        Assertions.assertEquals(userRepository.findById(user.getId()).get().getUsername(), user.getUsername());

    }

    @Test
    void delete() {
        UserDTO user = UserDTO.builder()
                .username(randomString())
                .email("email@email.com")
                .password(passwordEncoder.encode(randomString()))
                .role(EMPLOYEE.name())
                .build();
        user = userService.create(user);
        userService.delete(user.getId());
        Assertions.assertTrue(userRepository.findById(user.getId()).isEmpty());
    }
}