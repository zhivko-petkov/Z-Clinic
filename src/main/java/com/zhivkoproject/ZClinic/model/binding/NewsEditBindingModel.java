package com.zhivkoproject.ZClinic.model.binding;

import javax.validation.constraints.Size;

public class NewsEditBindingModel {
    private Long id;

    @Size(min = 3, max = 30)
    private String title;

    @Size(min = 10, max = 5000)
    private String content;

    private String imgUrl;

    public NewsEditBindingModel() {
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
}
