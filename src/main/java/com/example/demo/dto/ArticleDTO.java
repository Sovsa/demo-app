package com.example.demo.dto;

/**
 *
 * @author anton.nilsson
 */
public record ArticleDTO(Integer art_id, Integer amount_of) {

    public ArticleDTO(String art_id,
                      String amount_of) {
        this(Integer.parseInt(art_id), Integer.parseInt(amount_of));
    }
}
