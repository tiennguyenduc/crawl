package com.tnd.crawl.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private int order;

}
