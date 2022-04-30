package com.example.demo.user;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.item.model.dto.ItemDTO;
import com.example.demo.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.randomLong;
import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.UrlMapping.*;
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
        List<UserDTO> userListDTOs = TestCreationFactory.listOf(UserDTO.class);
        when(userService.allUsers()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void create() throws Exception {
        UserDTO reqUser = UserDTO.builder().username(randomString())
                .email(randomString())
                .password(randomString())
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(USERS, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        UserDTO reqUser = UserDTO.builder()
                .email(randomString())
                .username(randomString())
                .password(randomString())
                .build();

        when(userService.edit(id, reqUser)).thenReturn(reqUser);

        ResultActions result = performPutWithRequestBodyAndPathVariables(USERS + USERS_ID_EDIT, reqUser, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception{
        final long id = randomLong();
        UserDTO reqUser = UserDTO.builder()
                .email(randomString())
                .username(randomString())
                .password(randomString())
                .build();

        when(userService.edit(id, reqUser)).thenReturn(reqUser);
    }
}
