package com.example.demo.dto;

import java.util.List;

/**
 *
 * @author anton.nilsson
 */
public record ProductDTO(String name, Integer price, List<ArticleDTO> contain_articles) {

}
