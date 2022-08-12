package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.entity.News;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.user.ZClinicUserDetails;
import com.zhivkoproject.ZClinic.repository.NewsRepository;
import com.zhivkoproject.ZClinic.repository.UserRepository;
import com.zhivkoproject.ZClinic.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerMock {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    private static final String TITLE = "The increases COVID cases";

    private static final String CONTENT = "What we have to do if we have covid? " +
                                            "What we have to do if we have covid? " +
                                            "What we have to do if we have covid? " +
                                            "What we have to do if we have covid?";

    private static final String IMGURL = "https://api.time.com/wp-content/uploads/2019/08/better-smartphone-photos.jpg";

    private static LocalDate CREATEDON = LocalDate.now();

    private User addedBy;

    private ZClinicUserDetails user;



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
        userRepository.deleteAll();
        newsRepository.deleteAll();
    }

    @Test
    void testNewsPage() throws Exception{
        mockMvc.perform(get("/news")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("allNews")).
                andExpect(view().name("news-index"));
    }

    @Test
    void testAddNewsPage() throws Exception {
        mockMvc.perform(get("/news/add").
                        with(user(user)).with(csrf())
                ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("newsAddBindingModel")).
                andExpect(view().name("news-add"));
    }

    @Test
    void testAddNews() throws Exception {
        mockMvc.perform(post("/news/add").
                        with(user(user)).
                param("title", TITLE).
                param("content", CONTENT).
                param("imgUrl", IMGURL).
                with(csrf()).
                contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).
                andExpect(status().is(302)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/news"));
    }

    @Test
    void testAddInvalidNews() throws Exception {
        mockMvc.perform(post("/news/add").
                        with(user(user)).
                        param("title", "n").
                        param("content", "").
                        param("imgUrl", IMGURL).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).andExpect(model().attributeHasNoErrors()).
                andExpect(status().is(302)).
                andExpect(view().name("redirect:/news/add"));

    }

    @Test
    void testEditInvalidNews() throws Exception {
        News news = initNews();

        mockMvc.perform(post("/news/edit/" + news.getId()).
                        with(user(user)).
                        param("title", "n").
                        param("content", "").
                        param("imgUrl", IMGURL).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).andExpect(model().attributeHasErrors()).
                andExpect(status().is(302)).
                andExpect(view().name("redirect:/news/edit/{id}"));

    }

    @Test
    void testEditNewsPage() throws Exception {
        News news = initNews();

        mockMvc.perform(get("/news/edit/" + news.getId()).
                        with(user(user))
                ).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("newsEditBindingModel")).
                andExpect(model().attributeExists("newsId")).
                andExpect(view().name("news-edit"));
    }

    @Test
    void testEditNews() throws Exception {
        News news = initNews();
        mockMvc.perform(post("/news/edit/" + news.getId()).
                        with(user(user)).
                        param("title", TITLE).
                        param("content", CONTENT).
                        param("imgUrl", IMGURL).
                        with(csrf()).
                        contentType(MediaType.APPLICATION_FORM_URLENCODED)
                ).
                andExpect(status().is(302)).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/news"));
    }

    @Test
    void testDeleteNews() throws Exception {
        News news = initNews();
        mockMvc.perform(get("/news/delete/" + news.getId()).
                        with(user(user))
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/news"));
    }

    @Test
    void testHomePage() throws Exception{
        News news = initNews();
        mockMvc.perform(get("/news/" + news.getId())
                ).
                andExpect(status().is(200)).
                andExpect(model().attributeExists("news")).
                andExpect(view().name("news-home"));
    }


    @Test
    void testNewsSearchPage() throws Exception{
        mockMvc.perform(get("/news/search")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("newsSearchBindingModel")).
                andExpect(view().name("news-search"));
    }

    @Test
    void testNewsSearch() throws Exception{
        News news = initNews();

        mockMvc.perform(get("/news/search")
                        .param("title", news.getTitle())).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("newsSearchBindingModel")).
                andExpect(model().attributeExists("news")).
                andExpect(view().name("news-search"));

        mockMvc.perform(get("/news/search")
                        .param("title", "l")).
                andExpect(status().isOk()).
                andExpect(model().attributeHasErrors("newsSearchBindingModel")).
                andExpect(view().name("news-search"));


    }
    private News initNews() {
        News news = new News();
        news.setCreatedOn(LocalDate.now());
        news.setTitle(TITLE);
        news.setContent(CONTENT);
        news.setImgUrl(IMGURL);
        news.setCreatedOn(CREATEDON);
        news.setAddedBy(addedBy);

        News saved = newsRepository.save(news);

        return saved;

    }



}
