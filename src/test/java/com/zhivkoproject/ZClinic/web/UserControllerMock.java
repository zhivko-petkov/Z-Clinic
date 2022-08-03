package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerMock {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void refresh() { userRepository.deleteAll();}

    @Test
    void testLoginPageShown() throws Exception {
        mockMvc.perform(get("/users/login")).
                andExpect(status().isOk()).
                andExpect(view().name("login"));
    }

    @Test
    void testRegistrationPageShown() throws Exception {
        mockMvc.perform(get("/users/register")).
                andExpect(status().isOk()).
                andExpect(view().name("register"));
    }

    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("username", "tonka").
                        param("firstName", "Tonka").
                        param("surname", "Koleva").
                        param("email", "tonka@example.com").
                        param("password", "topsecret").
                        param("confirmPassword", "topsecret").
                        param("address", "Sofia").
                        param("additionalDetails", "She is a famous star.").
                        param("imageUrl", "https://bit.ly/3oN6btc")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        assertEquals(1, userRepository.count());

    }

    @Test
    public void registerFailPostShownTest() throws Exception {
        mockMvc.perform(post("/users/register").
                        param("username", "").
                        param("firstName", "Tonka").
                        param("surname", "Koleva").
                        param("email", "tonka@example.com").
                        param("password", "topsecret").
                        param("confirmPassword", "1").
                        param("address", "Sofia").
                        param("additionalDetails", "She is a famous star.").
                        param("imageUrl", "https://bit.ly/3oN6btc")
                        .with(csrf())
                ).andExpect(redirectedUrl("/users/register"));
        assertEquals(0, userRepository.count());
    }

    @Test
    public void testUserLogin() throws Exception {
        mockMvc.perform(post("/users/register").
                param("username", "tonka").
                param("firstName", "Tonka").
                param("surname", "Koleva").
                param("email", "tonka@example.com").
                param("password", "topsecret").
                param("confirmPassword", "topsecret").
                param("address", "Sofia").
                param("additionalDetails", "She is a famous star.").
                param("imageUrl", "https://bit.ly/3oN6btc")
                .with(csrf())
        );
        mockMvc.perform(post("/users/login").
                param("username", "tonka").
                param("password", "topsecret")
                .with(csrf())
        ).andExpect(redirectedUrl("/"));
        assertEquals(1, userRepository.count());
    }






}
