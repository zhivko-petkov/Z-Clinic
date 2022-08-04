package com.zhivkoproject.ZClinic.model.binding;

import java.time.LocalDate;

public class NewsSearchBindingModel {

    private String title;

    private LocalDate date;

    public NewsSearchBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isEmpty() {
        return (title == null || title.isEmpty()) && date == null;
    }
}
