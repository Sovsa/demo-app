/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.demo.dto;

import com.example.demo.dao.Article;

/**
 *
 * @author anton.nilsson
 */
public record ArticleModel(String name, Integer stock) {

    public ArticleModel(Article article) {
        this(article.getName(), article.getStock());
    }
}
