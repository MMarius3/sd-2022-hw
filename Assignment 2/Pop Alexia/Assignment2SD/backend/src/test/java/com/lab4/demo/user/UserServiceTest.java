package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lab4.demo.user.model.ERole.ADMIN;
import static com.lab4.demo.user.model.ERole.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository.deleteAll();
        roleRepository.deleteAll();
        roleRepository.save(new Role(1,ADMIN));
        roleRepository.save(new Role(2,EMPLOYEE));
        userService = new UserService(userRepository,roleRepository, userMapper,passwordEncoder);
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserMinimalDTO> all = userService.allUsersMinimal();

        assertEquals(users.size(), all.size());
    }

    @Test
    void create(){
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(EMPLOYEE.name())
                .build();
        User user = User.builder()
                .id(1L)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,EMPLOYEE)))
                .build();
        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        UserDTO createdUser = userService.create(userDTO);
        Assertions.assertEquals(createdUser,userMapper.toDto(user));
    }

    @Test
    void edit(){
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .roles(Set.of(new Role(2,EMPLOYEE)))
                .build();
        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .role(EMPLOYEE.name())
                .build();

        when(userMapper.toDto(user)).thenReturn(userDTO);
        when(userMapper.fromDto(userDTO)).thenReturn(user);
        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(user));
        when(userMapper.toDto(userRepository.save(userMapper.fromDto(userDTO)))).thenReturn(userDTO);
        UserDTO createdUser = userService.create(userDTO);
        Assertions.assertEquals("whatever",createdUser.getUsername());
        createdUser.setUsername("newwhatever");
        UserDTO editedUser = userService.edit(createdUser.getId(),createdUser);
        Assertions.assertEquals("newwhatever" ,editedUser.getUsername());

    }

    @Test
    void delete(){
        Long id = 1L;
        User user = User.builder()
                .id(id)
                .username("whatever")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .build();

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(user));
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        userService.delete(id);
        assertFalse(userService.existsByEmail(user.getEmail()));

    }
}
