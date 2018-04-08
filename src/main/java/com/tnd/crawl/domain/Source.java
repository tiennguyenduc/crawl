package com.tnd.crawl.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Source {
    SOHA("http://soha.vn", "vi", "a[href^=/]",
            "[data-field=title]", "[data-field=createddate]", "[data-field=author]", "[data-field=source]", "[data-field=body]"),
    XINHUANET("http://xinhuanet.com", "zh", "a[href^=http://www.xinhuanet.com/]",
            "div.h-title", "span.h-time", "span.p-jc", "em#source", "div#p-detail"),
    SCMP("http://scmp.com", "en", "a[href^=/news][href*=/article/]",
            "h1#page-title", "[itemprop=dateCreated]", "div.scmp-v2-author-name", "div.scmp-v2-author-name", "div.pane-content");

    private String url;

    private String language;

    private String query;

    private List<String> fields;

    Source(String url, String language, String query, String... fields) {
        this.url = url;
        this.language = language;
        this.query = query;
        this.fields = Arrays.asList(fields);
    }
}
