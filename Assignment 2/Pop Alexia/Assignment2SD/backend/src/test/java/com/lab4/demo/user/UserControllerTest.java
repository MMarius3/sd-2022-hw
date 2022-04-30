package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.book.BookMapper;
import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.BOOK;
import static com.lab4.demo.UrlMapping.USER;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
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
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void filterUsers() throws Exception {
        UserDTO userDTO1= UserDTO.builder()
                .id(randomLong())
                .username("wwwwwww")
                .email("email@email.com")
                .password(encodePassword("password"))
                .build();
        UserDTO userDTO2= UserDTO.builder()
                .id(randomLong())
                .username("qqqqqqq")
                .email("email2@email.com")
                .password(encodePassword("password"))
                .build();

        List<UserDTO> filteredUsers = List.of(userDTO1);

        when(userService.create(userDTO1)).thenReturn(userDTO1);
        when(userService.create(userDTO2)).thenReturn(userDTO2);
        when(userService.filterUsers("%ww%")).thenReturn(filteredUsers);

        ResultActions result1 = performPostWithRequestBody(USER, userDTO1);
        result1.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created successfully")));

        ResultActions result2 = performPostWithRequestBody(USER, userDTO2);
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created successfully")));

        ResultActions result3 = performGetWithPathVariable(USER+"/filter/{filter}","%ww%" );
        result3.andExpect(status().isOk()).andExpect(jsonContentToBe(filteredUsers));
    }

    @Test
    void create() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .username("username")
                .email("we@email.com")
                .password(encodePassword("whatever"))
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created successfully")));
    }

    @Test
    void edit() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(randomLong())
                .username("whatever3")
                .email("we2@email.com")
                .password(encodePassword("whatever3"))
                .build();
        when(userService.create(reqUser)).thenReturn(reqUser);
        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created successfully")));

        reqUser.setUsername(randomString());
        when(userService.edit(reqUser.getId(),reqUser)).thenReturn(reqUser);
        when(userService.findById(reqUser.getId())).thenReturn(reqUser);

        ResultActions result2 = performPutWithRequestBodyAndPathVariables(USER+"/{id}", reqUser,reqUser.getId());
        result2.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User edited successfully")));
    }

    @Test
    void delete() throws Exception {
        UserDTO reqUser = UserDTO.builder()
                .id(1L)
                .username("whatever2")
                .email("we3@email.com")
                .password(encodePassword("whatever2"))
                .build();

        when(userService.create(reqUser)).thenReturn(reqUser);
        ResultActions result = performPostWithRequestBody(USER, reqUser);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(new MessageResponse("User created successfully")));

        doNothing().when(userService).delete(reqUser.getId());

        ResultActions result2 = performDeleteWithPathVariable(USER+"/{id}", reqUser.getId());
        result2.andExpect(status().isOk()).andExpect(jsonContentToBe(new MessageResponse("User deleted successfully")));
        verify(userService, times(1)).delete(reqUser.getId());
    }

}