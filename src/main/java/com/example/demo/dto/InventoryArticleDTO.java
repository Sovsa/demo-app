package com.example.demo.dto;

public record InventoryArticleDTO(Integer art_id, String name, Integer stock) {

    public InventoryArticleDTO(String art_id,
                               String name,
                               String stock) {
        this(Integer.parseInt(art_id), name, Integer.parseInt(stock));
    }
}