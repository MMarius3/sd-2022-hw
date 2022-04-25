package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.UrlMapping.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    private UserMapper userMapper;

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
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void create() throws Exception {
        UserListDTO reqItem1 = UserListDTO.builder()
                .id(1L)
                .name("adriana26")
                .email("adriana@yahoo.com")
                .password("pass123232e")
                .roles(Set.of("EMPLOYEE"))
                .build();

        when(userService.create(reqItem1)).thenReturn(reqItem1);

        ResultActions result = performPostWithRequestBody(USER, reqItem1);

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem1));
    }

    @Test
    void update() throws Exception{
        Long id = 1L;
        UserListDTO reqItem1 = UserListDTO.builder()
                .id(1L)
                .name("adriana26")
                .email("adriana@yahoo.com")
                .password("pass123232e")
                .roles(Set.of("EMPLOYEE"))
                .build();

        when(userService.create(reqItem1)).thenReturn(reqItem1);
        when(userService.update(id,reqItem1)).thenReturn(reqItem1);

        ResultActions result = performPutWithRequestBodyAndPathVariables(USER + USERS_ID_PART, reqItem1, id);

        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem1));
    }

    @Test
    void delete() throws Exception {
        doNothing().when(userService).delete(1L);

        ResultActions resultActions = performDeleteWithPathVariable(USER + USERS_ID_PART, 1L);

        resultActions.andExpect(status().isOk());
    }
}