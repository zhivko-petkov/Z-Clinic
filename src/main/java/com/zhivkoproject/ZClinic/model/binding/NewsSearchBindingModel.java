package com.zhivkoproject.ZClinic.model.binding;

import javax.validation.constraints.Size;
import java.time.LocalDate;

public class NewsSearchBindingModel {

    @Size(min = 10)
    private String title;


    public NewsSearchBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isEmpty() {
        return (title == null || title.isEmpty());
    }
}
