package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.user.ZClinicUserDetails;
import com.zhivkoproject.ZClinic.repository.TestRepository;
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
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerMock {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    void setUp(){
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
        testRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    private User addedBy;

    private ZClinicUserDetails user;

    @Test
    void testIndexPage() throws Exception {
        mockMvc.perform(get("/tests")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("getAllMedicalTests")).
                andExpect(view().name("examination-index"));
    }

    @Test
    void testIndexPageAdd() throws Exception {
        mockMvc.perform(get("/tests/add").
                        with(user(user)).
                        with(csrf())).
                andExpect(status().isOk()).
                andExpect(view().name("examination-add"));
    }

    @Test
    void testIndexAdd() throws Exception {
        mockMvc.perform(post("/tests/add").
                        with(user(user)).
                        param("name", "FFF").
                        param("category", CategoryEnum.BIOCHEMICAL.name()).
                        param("material", "blood").
                        param("price", String.valueOf(BigDecimal.valueOf(3))).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(302)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/tests"));

        mockMvc.perform(post("/tests/add").
                        with(user(user)).
                        param("name", "F").
                        param("category", CategoryEnum.BIOCHEMICAL.name()).
                        param("material", "").
                        param("price", String.valueOf(BigDecimal.valueOf(3))).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(302)).
                andExpect(view().name("redirect:/tests/add"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(post("/tests/add").
                        with(user(user)).
                        param("name", "FFFd").
                        param("category", CategoryEnum.BIOCHEMICAL.name()).
                        param("material", "blood").
                        param("price", String.valueOf(BigDecimal.valueOf(3))).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(302)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/tests"));

        mockMvc.perform(get("/tests/delete/"+ 1).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/tests"));

    }

    @Test
    void testEdit() throws Exception {
        mockMvc.perform(post("/tests/add").
                        with(user(user)).
                        param("name", "FFF").
                        param("category", CategoryEnum.BIOCHEMICAL.name()).
                        param("material", "blood").
                        param("price", String.valueOf(BigDecimal.valueOf(3))).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(302)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/tests"));


        mockMvc.perform(get("/tests/edit/" + 1).
                        with(user(user)).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(200)).
                andExpect(model().attributeExists("medicalTestEditBindingModel")).
                andExpect(model().attributeExists("testId")).
                andExpect(view().name("examination-edit"));

        mockMvc.perform(post("/tests/edit/" + 1).
                        with(user(user)).
                        param("name", "FFG").
                        param("category", CategoryEnum.BIOCHEMICAL.name()).
                        param("material", "oral").
                        param("price", String.valueOf(BigDecimal.valueOf(3))).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(302)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/tests"));

        mockMvc.perform(post("/tests/edit/" + 1).
                        with(user(user)).
                        param("name", "").
                        param("category", CategoryEnum.BIOCHEMICAL.name()).
                        param("material", "oral").
                        param("price", String.valueOf(BigDecimal.valueOf(3))).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(302)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/tests/edit/{id}"));

    }

}
