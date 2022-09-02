package com.zhivkoproject.ZClinic.model.binding;

import com.zhivkoproject.ZClinic.model.entity.Category;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class MedicalTestAddBindingModel {
    @NotEmpty
    @Size(min = 3)
    private String name;

    @NotNull
    private CategoryEnum category;

    @NotNull
    @Size(min = 3)
    private String material;

    @Positive
    @NotNull
    private BigDecimal price;

    private String addedByUsername;

    private boolean isDelay;

    public boolean isDelay() {
        return isDelay;
    }


}
