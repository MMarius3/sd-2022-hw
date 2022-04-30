package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,roleRepository,userMapper,encoder);
    }

    @Test
    void create() {
        User user = User.builder()
                .id(1L)
                .username("adriana26")
                .email("adriana@yahoo.com")
                .password(encoder.encode("pass123232e"))
                .roles(Set.of(new Role(2, ERole.EMPLOYEE)))
                .build();

        UserListDTO userListDTO = UserListDTO.builder()
                .id(1L)
                .name("adriana26")
                .email("adriana@yahoo.com")
                .password(encoder.encode("pass123232e"))
                .roles(Set.of("EMPLOYEE"))
                .build();

        when(userMapper.userFromUserListDto(userListDTO)).thenReturn(user);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userListDTO);
        when(roleRepository.findByName(ERole.EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserListDTO userListDTO1 = userService.create(userListDTO);

        Assertions.assertNotNull(userListDTO1);
    }

    @Test
    void update() {
        User user = User.builder()
                .id(1L)
                .username("adriana26")
                .email("adriana@yahoo.com")
                .password(encoder.encode("pass123232e"))
                .roles(Set.of(new Role(2, ERole.EMPLOYEE)))
                .build();

        UserListDTO userListDTO = UserListDTO.builder()
                .id(1L)
                .name("adriana26")
                .email("adriana@yahoo.com")
                .password(encoder.encode("pass123232e"))
                .roles(Set.of("EMPLOYEE"))
                .build();

        when(userMapper.userFromUserListDto(userListDTO)).thenReturn(user);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userListDTO);
        when(roleRepository.findByName(ERole.EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserListDTO userListDTO1 = userService.create(userListDTO);

        userListDTO1.setName("Adriana");

        UserListDTO userListDTO2 = userService.update(userListDTO1.getId(),userListDTO1);

        Assertions.assertEquals("Adriana",userListDTO2.getName());
    }

    @Test
    void delete() {
        User user = User.builder()
                .id(1L)
                .username("adriana26")
                .email("adriana@yahoo.com")
                .password(encoder.encode("pass123232e"))
                .roles(Set.of(new Role(2, ERole.EMPLOYEE)))
                .build();

        when(roleRepository.findByName(ERole.EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.delete(1L);
    }
}