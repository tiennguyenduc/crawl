package com.tnd.crawl.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class News {

    @Id
    @GeneratedValue
    public int id;

    public String url;

    public String title;

    @Column(name = "published_at")
    public String createddate;

    public String author;

    public String source;

    @Column(name = "content")
    public String body;

    @Transient
    public String firstphoto;

    @Transient
    public String sapo;
}
