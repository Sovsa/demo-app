package com.example.demo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.example.demo.dao.Article;
import com.example.demo.dao.Product;
import com.example.demo.dao.ProductContent;
import com.example.demo.dto.ArticleDTO;
import com.example.demo.dto.InventoryArticleDTO;
import com.example.demo.dto.ProductDTO;

/**
 *
 * @author anton.nilsson
 */
public class TestUtil {

    public static Article getTestArticleA() {
        Article article = new Article();
        article.setArticleid(1);
        article.setName("Leg");
        article.setStock(6);

        return article;
    }

    public static Article getTestArticleB() {
        Article article = new Article();
        article.setArticleid(2);
        article.setName("Screw");
        article.setStock(20);

        return article;
    }

    public static Article getTestArticleC() {
        Article article = new Article();
        article.setArticleid(3);
        article.setName("Seat");
        article.setStock(2);

        return article;
    }

    public static InventoryArticleDTO getTestInventoryArticleDTOA() {
        Article testArticle = getTestArticleA();
        InventoryArticleDTO articleDTO = new InventoryArticleDTO(testArticle.getArticleid(), 
            testArticle.getName(), 
            testArticle.getStock());
        
        return articleDTO;
    }
    
    public static Product getTestProductA() {
        Product product = new Product();
        product.getProductContent().add(new ProductContent(getTestArticleA(), 4, product));
        product.getProductContent().add(new ProductContent(getTestArticleB(), 8, product));
        product.getProductContent().add(new ProductContent(getTestArticleC(), 1, product));
        product.setName("Chair");
        product.setPrice(new BigDecimal(20));

        return product;
    }

    public static ArticleDTO getTestArticleDTOA() {
        ArticleDTO articleDTO = new ArticleDTO(1, 4);
        return articleDTO;
    }

    public static ArticleDTO getTestArticleDTOB() {
        ArticleDTO articleDTO = new ArticleDTO(2, 8);
        return articleDTO;
    }

    public static ArticleDTO getTestArticleDTOC() {
        ArticleDTO articleDTO = new ArticleDTO(3, 1);
        return articleDTO;
    }

    public static ProductDTO getTestProductDTOA() {
        Product testProduct = getTestProductA();
        List<ArticleDTO> articleDTOList = Arrays.asList(getTestArticleDTOA(), getTestArticleDTOB(), getTestArticleDTOC());
        ProductDTO productDTO = new ProductDTO(testProduct.getName(), testProduct.getPrice().intValue(), articleDTOList);

        return productDTO;
    }
}
