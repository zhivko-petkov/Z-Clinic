package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.entity.Result;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class ResultAddBindingModel {
    private List<ResultServiceModel> resultsAdd;


    public void addResult(ResultServiceModel resultServiceModel){
        if (this.resultsAdd == null){
            this.resultsAdd = new ArrayList<>();
        }
        this.resultsAdd.add(resultServiceModel);
    }
}
