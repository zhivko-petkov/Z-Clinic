package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.repository.NewsRepository;
import com.zhivkoproject.ZClinic.repository.SubscriptionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerMock {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @AfterEach
    void refresh() {
        subscriptionRepository.deleteAll();
    }

    @Test
    public void testIndexShown() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("allNews"))
                .andExpect(model().attributeExists("subscritpionAddBindingModel"));

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctors"))
                .andExpect(model().attributeExists("doctors"));
    }

    @Test
    public void testSubscription() throws Exception {
        mockMvc.perform(post("/subscribe")
                .param("email", "example@example.bg")
                .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subscription"));
    }

    @Test
    public void testSubscriptionView() throws Exception {
        mockMvc.perform(get("/subscription")).
                andExpect(status().isOk())
                .andExpect(view().name("subscription"));
    }




}
