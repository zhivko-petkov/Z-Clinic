package com.zhivkoproject.ZClinic.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import com.zhivkoproject.ZClinic.repository.ResultRepository;
import com.zhivkoproject.ZClinic.service.ResultService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ResultServiceImpl implements ResultService {

    private static LocalDate dateOfLabUpdate;
    private static LocalDate dateOfBgUpdate;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ResultService.class);

    private final ResultRepository resultRepository;


    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }


    @Override
    public Map<String, Object> findAllNewCovidCasesInLab() {
        LOGGER.info("Getting all lab covid cases in last day.");
        String test = "SarsCOV2";
        dateOfLabUpdate = LocalDate.now();
        List<Result> results = resultRepository.findAllByTestByName(test);
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterday = nowDate.minusDays(1);
        long dayCases = 0;
        long allCases = 0;
        for (Result result : results) {
            if (result.getResult() != null){
                if (result.getResult().compareTo(BigDecimal.valueOf(0)) > 0){
                        allCases++;
                    if(result.getOrder().getCreatedOn().toLocalDate().compareTo(yesterday) == 0) {
                        dayCases++;
                    }
                }


            }

        }

        Map<String, Object> cases = new LinkedHashMap<>();
        cases.put("lastDayCases", dayCases);
        cases.put("labCases", allCases);
        cases.put("date", LocalDate.now());

        return cases;

    }


    @Override
    public Map<String, Object> findAllNewCovidCasesInBg() throws IOException, InterruptedException {
        LOGGER.info("Getting all covid cases in Bulgaria.");
        //save in cache new cases for the day.
        dateOfBgUpdate = LocalDate.now();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://disease.sh/v3/covid-19/historical/bg?lastdays=2"))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());
        String date = response.body().split("\"cases\":")[1].split(":")[0].split("\"")[1];
        String yesterdayCases = response.body().split("\"cases\":")[1].split(":")[1].split(",")[0];
        String todayCases = response.body().split("\"cases\":")[1].split(",")[1].split(":")[1];
        todayCases = todayCases.substring(0, todayCases.length()-1);

        long allCases = Long.parseLong(todayCases);
        long dailyCases = allCases - Long.parseLong(yesterdayCases);


        Map<String, Object> cases = new LinkedHashMap<>();
        cases.put("lastDayCases", dailyCases);
        cases.put("allCases", allCases);
        cases.put("date", date);

        return cases;

    }

    @Override
    @CacheEvict(cacheNames="covidCasesInBg", allEntries=true)
    public void refreshBgCache() {

    }

    @Override
    public LocalDate getDateOfBgUpdate() {
        return this.dateOfBgUpdate;
    }


    @Override
    @CacheEvict(cacheNames="covidCasesInLab", allEntries=true)
    public void refreshLabCache() {

    }

    @Override
    public LocalDate getDateOfLabUpdate() {
        return this.dateOfLabUpdate;
    }


}
