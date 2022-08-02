package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@RestController
public class CovidApiController {
    private final ResultService resultService;


    public CovidApiController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/api/covidInLab")
    @Transactional
    public ResponseEntity<Map<String, Object>> getAllNewCovidCasesInLastDay() {
        updateCacheLab();
        return new ResponseEntity<Map<String, Object>>(resultService.findAllNewCovidCasesInLab(), HttpStatus.OK);
    }


    @GetMapping("/api/covidInBg")
    @Transactional
    public ResponseEntity<Map<String, Object>> getAllBgCovidCases() throws IOException, InterruptedException {
        updateChacheBg();
        return new ResponseEntity<Map<String, Object>>(resultService.findAllNewCovidCasesInBg(), HttpStatus.OK);
    }

    private void updateChacheBg() {
        LocalDate dateOfUpdate = resultService.getDateOfBgUpdate();

        if(dateOfUpdate != null &&
                (dateOfUpdate.compareTo(LocalDate.now()) != 0))
            resultService.refreshBgCache();
    }

    private void updateCacheLab() {
        LocalDate dateOfUpdate = resultService.getDateOfLabUpdate();

        if(dateOfUpdate != null &&
                (dateOfUpdate.compareTo(LocalDate.now()) != 0))
            resultService.refreshLabCache();
    }


}
