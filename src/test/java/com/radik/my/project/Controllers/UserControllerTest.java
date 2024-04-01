package com.radik.my.project.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radik.my.project.MyFinalProjectApplication;
import com.radik.my.project.config.WebConfig;
import com.radik.my.project.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@ContextConfiguration(classes = {MyFinalProjectApplication.class, WebConfig.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void register_post_thenReturn400() throws Exception {
        mockMvc.perform(post("/register")
                .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_whenValidData_thenReturn200() throws Exception {
        when(userService.ifExistEmail(anyString())).thenReturn(false);
        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("firstName", "Batman");
        mapUser.put("lastName", "Hero");
        mapUser.put("email", "email@gmail.com");
        mapUser.put("password", "Aa1234");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(mapUser))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService, times(1)).create(any());
    }

    @Test
    void register_whenExistEmail_thenReturn409() throws Exception {
        when(userService.ifExistEmail(anyString())).thenReturn(true);
        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("firstName", "Batman");
        mapUser.put("lastName", "Hero");
        mapUser.put("email", "email@gmail.com");
        mapUser.put("password", "Aa1234");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(mapUser))
                        .with(csrf()))
                .andExpect(status().isConflict());

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        verify(userService, times(1)).ifExistEmail(emailCaptor.capture());
        assertThat(emailCaptor.getValue()).isEqualTo("email@gmail.com");
    }

    @Test
    void register_whenNotValidRequest_thenReturn400() throws Exception {
        when(userService.ifExistEmail(anyString())).thenReturn(false);
        Map<String, String> mapUser = new HashMap<>();
        mapUser.put("firstName", "Batman");
        mapUser.put("lastName", "");
        mapUser.put("email", "email@gmail.com");
        mapUser.put("password", "Aa1234");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(mapUser))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
//-------------------------------------------------------------------------------------------------
        Map<String, String> mapUser1 = new HashMap<>();
        mapUser1.put("firstName", "Batman");
        mapUser1.put("lastName", "Hero");
        mapUser1.put("email", "email@gmail.com");
        mapUser1.put("password", "Aaaaaaaaaaaaa");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(mapUser1))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
//-------------------------------------------------------------------------------------------------

        Map<String, String> mapUser2 = new HashMap<>();
        mapUser2.put("firstName", "Batman");
        mapUser2.put("lastName", "Hero");
        mapUser2.put("email", "emailgmail.com");
        mapUser2.put("password", "Aa1234");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(mapUser2))
                        .with(csrf()))
                .andExpect(status().isBadRequest());

//-------------------------------------------------------------------------------------------------

        Map<String, String> mapUser3 = new HashMap<>();
        mapUser3.put("firstName", "Batman");
        mapUser3.put("lastName", "Hero");
        mapUser3.put("email", "email@gmail.com");
        mapUser3.put("password", "Aa1234");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(mapUser3))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}
