package com.zhivkoproject.ZClinic.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "news")
@NoArgsConstructor
@Data
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

}
