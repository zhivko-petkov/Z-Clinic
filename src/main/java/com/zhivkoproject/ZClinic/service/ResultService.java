package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;

import java.util.List;

public interface ResultService {
    List<ResultServiceModel> orderResults(Long userId, Long orderId);
}
