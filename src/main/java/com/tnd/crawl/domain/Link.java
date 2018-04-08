package com.tnd.crawl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Link {

    @Id
    @GeneratedValue
    private int id;

    private String url;

    @Enumerated
    private Source source;

    private int crawled = 0;

    public Link(String url, Source source) {
        this.url = url;
        this.source = source;
    }
}
