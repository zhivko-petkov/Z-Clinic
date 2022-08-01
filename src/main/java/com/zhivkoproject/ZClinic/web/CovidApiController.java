package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidApiController {
    private final ResultService resultService;

    public CovidApiController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/api/covidInLastDay")
    @Transactional
    public ResponseEntity<Long> getAllNewCovidCasesInLastDay() {

        long cases = resultService.findAllNewCovidCasesInLastDay();
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @GetMapping("/api/covidInLab")
    @Transactional
    public ResponseEntity<Long> getAllCovidCasesInLab() {

        long cases = resultService.findAllCovidCasesInLab();
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @GetMapping("/api/covidInBgLastDay")
    @Transactional
    public ResponseEntity<Long> getAllNewBgCovidCasesInLastDay() {

        long cases = resultService.findAllNewBgCovidCasesInLastDay();
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }


}
