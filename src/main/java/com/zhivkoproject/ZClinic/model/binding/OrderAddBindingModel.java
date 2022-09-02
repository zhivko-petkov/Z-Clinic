package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderAddBindingModel {
    private List<MedicalTestServiceModel> tests;

}
