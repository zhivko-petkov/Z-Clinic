package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import com.zhivkoproject.ZClinic.repository.ResultRepository;
import com.zhivkoproject.ZClinic.service.ResultService;
import org.springframework.stereotype.Service;

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


}
