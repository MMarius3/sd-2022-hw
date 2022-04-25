package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.ItemController;
import com.lab4.demo.item.ItemService;
import com.lab4.demo.item.dto.ItemDto;
import com.lab4.demo.item.model.Item;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.user.dto.UserDto;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.randomLong;
import static com.lab4.demo.TestCreationFactory.randomString;
import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.CSV;
import static com.lab4.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;


    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allRegularUsers() throws Exception {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userService.allRegularUsers()).thenReturn(users);

        ResultActions response = performGet(USERS);

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));

    }

    @Test
    void create() throws Exception {
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        UserDto reqUser= UserDto.builder()
                .username(randomString())
                .email(email)
                .password(password)
                .build();

        when(userService.addUser(reqUser)).thenReturn(reqUser);

        ResultActions result = performPostWithRequestBody(USERS, reqUser);

        verify(userService, times(1)).addUser(reqUser);

        ResultMatcher jsonReqUser = jsonContentToBe(reqUser);
        result.andExpect(status().isCreated());
        MvcResult mvcResult = result.andReturn();
        jsonReqUser.match(mvcResult);
    }

    @Test
    void edit() throws Exception {
        final long id = randomLong();
        String email = "email@employee.com";
        String password = "Abcdefg1234!";
        UserDto reqUser= UserDto.builder()
                .username(randomString())
                .email(email)
                .password(password)
                .build();

        when(userService.edit(id, reqUser)).thenReturn(reqUser);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + USERS_ID_PART, reqUser, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();

        when(userService.delete(id)).thenReturn(true);

        ResultActions result = performDeleteWIthPathVariable(USERS + DELETE, id);

        result.andExpect(status().isOk());

    }
}
