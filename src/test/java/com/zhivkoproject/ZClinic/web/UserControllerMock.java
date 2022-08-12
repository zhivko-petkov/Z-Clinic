package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.user.ZClinicUserDetails;
import com.zhivkoproject.ZClinic.repository.UserRepository;
import com.zhivkoproject.ZClinic.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerMock {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    private User addedBy;

    private ZClinicUserDetails user;

    @BeforeEach
    void setUp() {

        User user = new User();

        //ROLE ADMIN
        UserRole userRole = new UserRole();
        userRole.setUserRole(UserRoleEnum.ADMIN);
        userRole = userRoleRepository.save(userRole);


        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);


        user.setAddress("Stara Zagora");
        user.setImageUrl("https://api.time.com/wp-content/uploads/2019/08/better-smartphone-photos.jpg");
        user.setEmail("stoyan@zclinic.bg");
        user.setFirstName("Stoyan");
        user.setSurname("Marinov");
        user.setUsername("stoyan");
        user.setAdditionalDetails("I'm famous person who likes.......");
        user.setPassword("12345");
        user.setUserRoles(roles);

        List<GrantedAuthority> authorities = user.getUserRoles().stream().map(r -> {
            return new SimpleGrantedAuthority("ROLE_".concat(r.getUserRole().name()));
        }).collect(Collectors.toList());

        Long userId = user.getId();


        addedBy = userRepository.save(user);
        this.user = new ZClinicUserDetails(user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getSurname(),
                authorities);


    }

    @AfterEach
    void refresh() {

        userRepository.deleteAll();

    }

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
                andExpect(model().attributeExists("isUsernameUnique")).
                andExpect(model().attributeExists("isEmailUnique")).
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

        assertEquals(2, userRepository.count());

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
        assertEquals(1, userRepository.count());

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
        assertEquals(2, userRepository.count());

    }

    @Test
    public void testUserLoginFailed() throws Exception {

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

        mockMvc.perform(post("/users/login").
                param("username", "tonka").
                param("password", "topsecretataa")
                .with(csrf())
        ).andExpect(status().isOk()).andExpect(forwardedUrl("/users/login-error"));

        mockMvc.perform(post("/users/login-error").
                param("username", "tonka").
                param("password", "topsecret")
                .with(csrf())
        ).andExpect(redirectedUrl("http://localhost/users/login"));

    }

    @Test
    public void testProfileView() throws Exception {

        mockMvc.perform(get("/users/profile").with(user(user))).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("userEditBindingModel")).
                andExpect(view().name("user-home"));


    }

    @Test
    public void testChangePassword() throws Exception {

        mockMvc.perform(get("/users/profile/changePassword").with(user(user))).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("errorOldPassword")).
                andExpect(view().name("user-password"));

    }


    @Test
    public void testResetPassword() throws Exception {

        mockMvc.perform(get("/users/profile/resetPassword/" + 1).
                        with(user(user)).
                        with(csrf())).
                andExpect(view().name("redirect:/users"));

    }

    @Test
    public void testAddUser() throws Exception {

        mockMvc.perform(get("/users/add").
                        with(user(user)).
                        with(csrf())).
                andExpect(model().attributeExists("isUsernameUnique")).
                andExpect(model().attributeExists("isEmailUnique")).
                andExpect(status().isOk()).
                andExpect(view().name("user-add"));

    }

    @Test
    public void indexPageShown() throws Exception {

        mockMvc.perform(get("/users").
                        with(user(user)).
                        with(csrf())).
                andExpect(model().attributeExists("getAllUsers")).
                andExpect(status().isOk()).
                andExpect(view().name("user-index"));


    }

    @Test
    public void testDeleteUser() throws Exception {

        mockMvc.perform(get("/users/delete/" + 1).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/users"));


    }

    @Test
    public void testDeleteLoggedUser() throws Exception {

        mockMvc.perform(get("/users/delete/" + addedBy.getId()).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/users/logout"));

    }

    @Test
    public void testEditUser() throws Exception {

        mockMvc.perform(get("/users/edit/" + 1).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("userEditBindingModel")).
                andExpect(model().attributeExists("userId")).
                andExpect(view().name("user-edit"));

    }


}
