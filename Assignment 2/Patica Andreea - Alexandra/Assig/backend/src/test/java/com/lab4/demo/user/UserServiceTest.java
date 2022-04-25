package com.lab4.demo.user;

import com.lab4.demo.item.ItemRepository;
import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.user.dto.UserDto;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, roleRepository);
    }

    @Test
    void findAll() {
        List<User> users = new ArrayList<>();
        int nrUsers = 10;
        for (int i = 0; i < nrUsers; i++) {
            users.add(User.builder().username(String.valueOf(i)).build());
        }

        when(userRepository.findAll()).thenReturn(users);

        List<UserMinimalDTO> all = userService.allUsers();

        Assertions.assertEquals(nrUsers, all.size());
    }


    @Test
    void addUser(){
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User reqUser= User.builder()
                .username(randomString())
                .email(email)
                .password(password)
                .build();

        UserDto userDto = UserDto.toDto(reqUser);

        when(userRepository.save(reqUser)).thenReturn(reqUser);

        UserDto savedUserDto = userService.addUser(userDto);

        Assertions.assertEquals(savedUserDto, userDto);
    }

    @Test
    void delete(){
        long id = randomLong();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        User reqUser= User.builder()
                .id(id)
                .username(randomString())
                .email(email)
                .password(password)
                .build();

        userRepository.save(reqUser);

        doNothing().when(userRepository).deleteById(id);

        boolean result = userService.delete(id);
        Assertions.assertEquals(result, true);
    }
}
