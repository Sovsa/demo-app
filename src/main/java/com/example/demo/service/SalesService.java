package com.example.demo.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Product;

@Service
public class SalesService {
    

    private ArticleService articleService;
    private ProductService productService;

    public SalesService(@Autowired ArticleService articleService,
                        @Autowired ProductService productService) {
        this.articleService = articleService;
        this.productService = productService;
    }

    /**
     * Buys and removes stock articles depending on the corresponding product of the productid needs.
     * @param productid Id of the product that is bought
     * @return The bought {@link Product}
     * @throws SQLException In case we cannot find an {@link Article} needed for the product
     * @throws IllegalArgumentException In case there is not enough stock
     */
    public Product buyProduct(Integer productid) throws SQLException, IllegalArgumentException {
        Product product = productService.getProductByProductId(productid);
        boolean stockAvailability = articleService.checkStockAvailability(product);
        if (!stockAvailability) {
            throw new IllegalArgumentException("There is not enough stock to buy this product");
        }

        //"Buy" the product
        articleService.removeItemsFromStock(product);
        return product;
    }
}
