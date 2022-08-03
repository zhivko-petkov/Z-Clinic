package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerMock {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsRepository newsRepository;


}
