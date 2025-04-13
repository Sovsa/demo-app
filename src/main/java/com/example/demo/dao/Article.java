package com.example.demo.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author anton.nilsson
 */
@Entity(name = "Article")
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "articleid", unique = true, nullable = false)
    private Integer articleid;
    private String name;
    private Integer stock;

    public Article(String name, Integer stock) {
        this.name = name;
        this.stock = stock;
    }

    public Article() {}

    public Integer getArticleid() {
        return articleid;
    }
    public void setArticleid(Integer articleId) {
        this.articleid = articleId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
