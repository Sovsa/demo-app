package com.example.demo.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Article;
import com.example.demo.dao.Product;
import com.example.demo.dao.ProductContent;
import com.example.demo.dto.ArticleDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ArticleService articleService;

    public ProductService(@Autowired ProductRepository productRepository,
                          @Autowired ArticleService articleService) {
        this.productRepository = productRepository;
        this.articleService = articleService;
    }

    /**
     * Saves a new {@link Product} to the database
     * @param productDTO POJO containing information for creating a new product
     * @return The saved {@link Product}
     * @throws SQLException In case we cannot find an {@link Article} needed for the product
     */
    public Product saveProduct(ProductDTO productDTO) throws SQLException {
        Product product = new Product();
        product.setName(productDTO.name());
        if(productDTO.price() != null) {
            product.setPrice(new BigDecimal(productDTO.price()));
        }

        for (ArticleDTO article : productDTO.contain_articles()) {
            Article articleById = articleService.getArticleById(article.art_id());
            if (articleById == null) {
                throw new SQLException("Could not find article with id: " + article.art_id());
            }

            ProductContent productContent = new ProductContent(articleById, article.amount_of(), product);
            product.getProductContent().add(productContent);
        }

        productRepository.save(product);
        return product;
    }

    /**
     * Gets all products
     * @return a {@link List} of {@link Product}s
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Get a product of the given productid
     * @param id Id correlating to the needed {@link Product}
     * @return {@link Product}
     */
    public Product getProductByProductId(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Checks how many of the given {@link Product} is available depending on current stock
     * @param product {@link Product} that we want to check.
     * @return the number of available {@link Product}s that we have on stock
     */
    public Integer getProductsAvailableByStock(Product product) {
        List<Float> articlesForProductAvailable = new ArrayList<>();
        for (ProductContent content : product.getProductContent()) {
            Float amountOfProductsAvilable = (float) content.getArticle().getStock() / (float) content.getAmountNeeded();
            if (amountOfProductsAvilable < 1) {
                amountOfProductsAvilable = 0f;
            }
            articlesForProductAvailable.add(amountOfProductsAvilable);
        }
        return (int) Math.floor(findMin(articlesForProductAvailable));
    }

    private static Float findMin(List<Float> numbers) {
        Float min = Float.MAX_VALUE;
        for (Float num : numbers) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }
}
