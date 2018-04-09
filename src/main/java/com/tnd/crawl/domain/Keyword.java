package com.tnd.crawl.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Keyword {

    @Id
    @GeneratedValue
    private int id;

    private String keyword;

    private String language;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public Category category;
}
