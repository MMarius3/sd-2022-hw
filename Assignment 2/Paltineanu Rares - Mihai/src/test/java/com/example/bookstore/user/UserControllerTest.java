package com.example.bookstore.user;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.user.dto.UserDTO;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.model.ERole;
import com.example.bookstore.user.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.bookstore.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER + GET_USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void addUser() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, ERole.ADMIN));
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .email("myemail@aaa.com")
                .name("username")
                .password("hallelujah")
                .roles(roles)
                .build();
        when(userService.create(userDTO)).thenReturn(userDTO);
        ResultActions result = performPostWithRequestBody(USER + ADD_USER, userDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

}