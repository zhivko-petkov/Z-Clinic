package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import com.zhivkoproject.ZClinic.repository.ResultRepository;
import com.zhivkoproject.ZClinic.service.ResultService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;

    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public List<ResultServiceModel> orderResults(Long userId, Long orderId) {
        return null;
    }

    @Override
    public long findAllNewCovidCasesInLastDay() {
        String test = "SarsCOV2";
        List<Result> results = resultRepository.findAllByTestByName(test);
        LocalDate nowDate = LocalDate.now();
        LocalDate yesterday = nowDate.minusDays(1);
        long cases = 0;
        for (Result result : results) {
            if (result.getResult() != null){
                if ((result.getResult().compareTo(BigDecimal.valueOf(0)) > 0) &&
                        result.getOrder().getCreatedOn().toLocalDate().compareTo(yesterday) == 0){
                    cases++;
                }
            }

        }

        return cases;

    }

    @Override
    public long findAllCovidCasesInLab() {
        String test = "SarsCOV2";
        List<Result> results = resultRepository.findAllByTestByName(test);
        LocalDate nowDate = LocalDate.now();

        long cases = 0;
        for (Result result : results) {
            if (result.getResult() != null){
                if ((result.getResult().compareTo(BigDecimal.valueOf(0)) > 0)){
                    cases++;
                }
            }

        }

        return cases;
    }

    @Override
    public long findAllNewBgCovidCasesInLastDay() {

        //save in cache new cases for the day.

        //TODO
        return 0;

    }


}
