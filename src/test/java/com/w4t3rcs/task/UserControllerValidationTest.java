package com.w4t3rcs.task;

import com.w4t3rcs.task.controller.UserController;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerValidationTest {
    private static final String USERS_URI = "http://localhost:8080/api/v1.0/users";
    private final MockMvc mockMvc;

    @Autowired
    public UserControllerValidationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @SneakyThrows
    @Test
    void createValidUser() {
        String user = "{\"firstName\": \"Firsttest\", \"lastName\": \"Lasttest\", \"email\": \"test@test.test\", \"birthDate\": \"2001-01-01\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(USERS_URI)
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    void createInvalidUser() {
        String user = "{\"firstName\": \"Firsttest\", \"lastName\": \"Lasttest\", \"email\": \"test@test.test\", \"birthDate\": \"2001-01-01\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(USERS_URI)
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
