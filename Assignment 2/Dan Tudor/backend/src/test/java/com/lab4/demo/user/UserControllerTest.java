package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.BookController;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
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
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    public void users() throws Exception {
        List<UserDTO> usersDTO = TestCreationFactory.listOf(UserDTO.class);
        when(userService.findAll()).thenReturn(usersDTO);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(usersDTO));
    }

    @Test
    public void create() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .build();
        when(userService.createUser(userDTO)).thenReturn(userDTO);

        ResultActions result = performPostWithRequestBody(USERS, userDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

    @Test
    public void delete() throws Exception {
        long id = randomLong();
        doNothing().when(userService).deleteUser(id);

        ResultActions result = performDeleteWIthPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk());

    }

    @Test
    public void update() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .id(randomLong())
                .username(randomString())
                .password(randomString())
                .email(randomString())
                .build();

        when(userService.updateUser(userDTO.getId(), userDTO)).thenReturn(userDTO);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, userDTO, userDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

}