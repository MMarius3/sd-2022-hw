package com.lab4.demo.user;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.BookMapper;
import com.lab4.demo.book.BookRepository;
import com.lab4.demo.book.BookService;
import com.lab4.demo.book.model.Book;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

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
    private ReportServiceFactory reportServiceFactory;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,roleRepository, userMapper,passwordEncoder);
    }

    @Test
    void createTest() {
     UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .roles(new HashSet<>(Set.of(EMPLOYEE.name())))
                .email("email@yahoo.com")
                .build();
        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userService.createUser(userDTO)).thenReturn(userDTO);
        UserListDTO createdUser = userService.createUser(userDTO);
        Assertions.assertEquals(createdUser,userDTO);
    }

    @SneakyThrows
    @Test
    void updateTest() {
        Role defaultRole = Role.builder().id(358).name(ERole.EMPLOYEE).build();
        User user = User.builder()
                .id(1L)
                .username("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(defaultRole))
                .build();
        UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("doro")
                .email("doro@yahoo.com")
                .password("password1")
                .roles(Set.of(EMPLOYEE.name()))
                .build();

        when(roleRepository.findByName(EMPLOYEE)).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userMapper.userListDtoFromUser(userRepository.save(userMapper.userfromUserListDto(userDTO)))).thenReturn(userDTO);

        UserListDTO createdUser = userService.createUser(userDTO);
        createdUser.setName("new username");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
        when(userMapper.userListDtoFromUser(userRepository.save(userMapper.userfromUserListDto(userDTO)))).thenReturn(userDTO);

        UserListDTO editedUser = userService.updateUser(createdUser.getId(),createdUser);
        Assertions.assertNotEquals("doro" ,editedUser.getName());
    }

    @Test
    void delete(){
        UserListDTO userDTO = UserListDTO.builder()
                .id(1L)
                .name("test")
                .password("test")
                .roles(new HashSet<>(Set.of(EMPLOYEE.name())))
                .email("email@yahoo.com")
                .build();
        userRepository.save(userMapper.userfromUserListDto(userDTO));
        userService.deleteUser(userDTO.getId());
        Assertions.assertEquals(0,userRepository.findAll().size());
    }


}
