package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ResultService {

    @Cacheable("covidCasesInLab")
    Map<String, Object> findAllNewCovidCasesInLab();

    void refreshLabCache();

    LocalDate getDateOfLabUpdate();

    @Cacheable("covidCasesInBg")
    Map<String, Object> findAllNewCovidCasesInBg() throws IOException, InterruptedException;

    void refreshBgCache();

    LocalDate getDateOfBgUpdate();


}
