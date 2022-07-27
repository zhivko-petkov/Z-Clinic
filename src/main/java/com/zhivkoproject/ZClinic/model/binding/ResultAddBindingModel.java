package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;

import java.util.ArrayList;
import java.util.List;

public class ResultAddBindingModel {
    private List<ResultServiceModel> resultsAdd;

    public ResultAddBindingModel() {
    }

    public List<ResultServiceModel> getResultsAdd() {
        return resultsAdd;
    }

    public void setResultsAdd(List<ResultServiceModel> resultsAdd) {
        this.resultsAdd = resultsAdd;
    }

    public void addResult(ResultServiceModel resultServiceModel){
        if (this.resultsAdd == null){
            this.resultsAdd = new ArrayList<>();
        }
        this.resultsAdd.add(resultServiceModel);
    }
}
