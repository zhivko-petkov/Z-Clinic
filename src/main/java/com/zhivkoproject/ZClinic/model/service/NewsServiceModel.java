package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class NewsServiceModel {

    private Long id;
    private String title;
    private String content;
    private String imgUrl;
    private User addedBy;
    private LocalDate createdOn;

}
