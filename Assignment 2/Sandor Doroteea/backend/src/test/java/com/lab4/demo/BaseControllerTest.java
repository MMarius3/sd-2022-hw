package com.lab4.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public abstract class BaseControllerTest {
    protected MockMvc mockMvc;

    @BeforeEach
    protected void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected ResultMatcher jsonContentToBe(Object expectedJsonContent) throws JsonProcessingException {
        return content().json(new ObjectMapper().writeValueAsString(expectedJsonContent), true);
    }

    protected ResultActions performPostWithRequestBody(String path, Object body) throws Exception {
        return mockMvc.perform(post(path)
                .content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performPutWithRequestBody(String path, Object body) throws Exception {
        return mockMvc.perform(put(path)
                .content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performPutWithRequestBodies(String path, Object body,Object obj) throws Exception {
        return mockMvc.perform(put(path)
                .content(asJsonString(body))
                .content(asJsonString(obj))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
    protected ResultActions performGet(String path) throws Exception {
        return mockMvc.perform(get(path));
    }
    protected ResultActions performGetWithRequestBody(String path,Object body) throws Exception {
        return mockMvc.perform(get(path)
                .content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performGetWithPathVariable(String path,Object... pathVariables) throws Exception {
        return mockMvc.perform(get(path,pathVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }


    protected ResultActions performPutWithRequestBodyAndPathVariables(String path, Object body, Object... pathVariables) throws Exception {
        return mockMvc.perform(put(path, pathVariables)
                .   content(asJsonString(body))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    protected ResultActions performDeleteWithPathVariable(String path, Long id) throws Exception {
        return mockMvc.perform(delete(path, id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }



    protected String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
