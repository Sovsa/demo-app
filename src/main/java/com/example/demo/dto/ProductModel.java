package com.example.demo.dto;

import java.math.BigDecimal;

import com.example.demo.dao.Product;

public record ProductModel(Integer productid, String name, BigDecimal price, Integer amountAvailable) {
    
    public ProductModel(Product product, Integer amountAvailable) {
        this(product.getProductid(), product.getName(), product.getPrice(), amountAvailable);
    }
}
