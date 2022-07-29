package com.zhivkoproject.ZClinic.model.service;

import com.zhivkoproject.ZClinic.model.entity.User;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class NewsServiceModel {

    private Long id;
    private String title;
    private String content;
    private String imgUrl;
    private User addedBy;
    private LocalDate createdOn;

    public NewsServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
}
