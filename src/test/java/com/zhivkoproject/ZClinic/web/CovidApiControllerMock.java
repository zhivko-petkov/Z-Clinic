package com.zhivkoproject.ZClinic.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CovidApiControllerMock {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllNewCovidCasesInLastDay() throws Exception {
        mockMvc.perform(get("/api/covidInLab"))
                .andExpect(status().is(200));
    }

    @Test
    public void getAllBgCovidCases() throws Exception {
        mockMvc.perform(get("/api/covidInBg"))
                .andExpect(status().is(200));
    }

    @Test
    public void updateChache() throws Exception {

    }
}
