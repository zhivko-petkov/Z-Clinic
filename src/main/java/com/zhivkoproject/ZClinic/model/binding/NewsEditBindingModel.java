package com.zhivkoproject.ZClinic.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class NewsEditBindingModel {
    private Long id;

    @Size(min = 5, max = 100)
    private String title;

    @Size(min = 50, max = 5000)
    private String content;

    private String imgUrl;

}
