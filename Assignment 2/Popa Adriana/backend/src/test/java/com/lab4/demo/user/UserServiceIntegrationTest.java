package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1, ERole.ADMIN));
        roleRepository.save(new Role(2, ERole.EMPLOYEE));
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
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getName());
        }
    }

    @Test
    void create(){
        UserListDTO userListDTO = userService.create(UserListDTO.builder()
                .name("adriana26")
                .password("123mypass45")
                .email("adriana@yahoo.com")
                .roles(Set.of("EMPLOYEE"))
                .build());

        Assertions.assertNotNull(userListDTO);
    }

    @Test
    void update(){
        UserListDTO userListDTO = userService.create(UserListDTO.builder()
                .id(1L)
                .name("adriana26")
                .password("123mypass45")
                .email("adriana@yahoo.com")
                .roles(Set.of("EMPLOYEE"))
                .build());

        Assertions.assertNotNull(userListDTO);

        userListDTO.setEmail("adriana26@yahoo.com");

        UserListDTO userUpdated = userService.update(userListDTO.getId(), userListDTO);

        Assertions.assertEquals("adriana26@yahoo.com", userUpdated.getEmail());
    }

    @Test
    void delete(){
        UserListDTO userListDTO = userService.create(UserListDTO.builder()
                .name("adriana26")
                .password("123mypass45")
                .email("adriana@yahoo.com")
                .build());

        userService.delete(userListDTO.getId());

        List<User> users= userRepository.findAll();
        Assertions.assertEquals(0,users.size());
    }
}