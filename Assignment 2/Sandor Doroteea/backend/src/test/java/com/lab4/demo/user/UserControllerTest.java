package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userDTOs = new ArrayList<>();
        userDTOs.add(UserListDTO.builder()
                .email("email@yahoo.com")
                .password("password")
                .name("username")
                .id(1L)
                .build());
        userDTOs.add(UserListDTO.builder()
                .email("emaiql@yahoo.com")
                .password("password1")
                .name("username1")
                .id(2L)
                .build());
        when(userService.allUsersForList()).thenReturn(userDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTOs));
    }

    @Test
    void create() throws Exception {
        Set<String> roles = new HashSet<>();
        //Role defaultRole = Role.builder().name(ERole.EMPLOYEE).build();
        roles.add("EMPLOYEE");
        UserListDTO reqDTO = UserListDTO.builder()
                .email("email@yahoo.com")
                .password("password")
                .name("username")
                .roles(roles)
                .id(1L)
                .build();
        when(userService.createUser(reqDTO)).thenReturn(reqDTO);
        //System.out.println(reqDTO.getRoles().stream().findFirst().get());
        ResultActions result = performPostWithRequestBody(USER, reqDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created")));
    }

    @Test
    void update() throws Exception {
        Set<String> roles = new HashSet<>();
        //Role defaultRole = Role.builder().name(ERole.EMPLOYEE).build();
        roles.add("EMPLOYEE");
        UserListDTO reqDTO = UserListDTO.builder()
                .email("email@yahoo.com")
                .password("password")
                .name("username")
                .roles(roles)
                .id(1L)
                .build();
        when(userService.createUser(reqDTO)).thenReturn(reqDTO);
        when(userService.updateUser(reqDTO.getId(),reqDTO)).thenReturn(reqDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariables(USER+USER_ID_PART, reqDTO, reqDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User updated")));
    }

    @Test
    void delete() throws Exception{
        UserListDTO userDTO = UserListDTO.builder()
                .email("email")
                .password("password")
                .name("username")
                .id(1L)
                .build();

        when(userService.createUser(userDTO)).thenReturn(userDTO);
        userService.deleteUser(userDTO.getId());

        ResultActions response = performDeleteWithPathVariable(USER + USER_ID_PART,userDTO.getId());
        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User deleted")));
    }


}
