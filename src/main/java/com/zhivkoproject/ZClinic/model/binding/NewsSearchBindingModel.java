package com.zhivkoproject.ZClinic.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class NewsSearchBindingModel {
    @Size(min = 10)
    private String title;

    public boolean isEmpty() {
        return (title == null || title.isEmpty());
    }
}
