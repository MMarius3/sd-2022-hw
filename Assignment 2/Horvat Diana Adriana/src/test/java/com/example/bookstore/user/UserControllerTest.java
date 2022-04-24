package com.example.bookstore.user;


import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.book.model.Book;
import com.example.bookstore.book.model.dto.BookDTO;
import com.example.bookstore.user.dto.UserListDTO;
import com.example.bookstore.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.bookstore.TestCreationFactory.*;
import static com.example.bookstore.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
        List<UserListDTO> users = TestCreationFactory.listOf(User.class);
        when(userService.allUsersForList()).thenReturn(users);

        ResultActions response = mockMvc.perform(get(USERS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));

    }

//    @Test
//    void create() throws Exception {
//        Set<String> roles = new HashSet<>();
//        roles.add("EMPLOYEE");
////
//        UserListDTO reqItem = UserListDTO.builder()
////                .id(randomLong())
////                .name(randomString())
//                .email(randomString())
//                .password(randomString())
//                .roles(roles)
//                .build();
//
//        when(userService.create(reqItem)).thenReturn(reqItem);
//
//        ResultActions result = performPostWithRequestBody(USERS, reqItem);
//        result.andExpect(status().isOk());
//    }


//
//    @Test
//    void edit() throws Exception {
//        Set<String> roles = new HashSet<>();
//        roles.add("EMPLOYEE");
//        long id = randomLong();
//
//        UserListDTO reqItem = UserListDTO.builder()
////                .id(randomLong())
////                .name(randomString())
//                .email(randomString())
//                .password(randomString())
//                .roles(roles)
//                .build();
//
//        when(userService.edit(id, reqItem)).thenReturn(reqItem);
//
//        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, reqItem, id);
//        result.andExpect(status().isOk())
//                .andExpect(jsonContentToBe(reqItem));
//    }


}