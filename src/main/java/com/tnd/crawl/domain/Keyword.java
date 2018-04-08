package com.tnd.crawl.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Keyword {

    @Id
    @GeneratedValue
    private int id;

    private String keyword;

    private String language;
}
