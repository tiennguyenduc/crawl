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

    public String publishedAt;

    public String author;

    public String copyright;

    public String content;

    public String tag;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id")
    public Link link;

    @Enumerated(EnumType.STRING)
    public Source source;
}
