package com.zhivkoproject.ZClinic.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "news")
public class News extends BaseEntity{
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, length = 100000)
    private String content;

    @Column(nullable = false)
    private String imgUrl;

    @ManyToOne
    private User addedBy;

    @Column(nullable = false)
    private LocalDate createdOn;

    public News() {
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
