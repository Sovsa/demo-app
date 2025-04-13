package com.example.demo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Article;
import com.example.demo.dao.Product;
import com.example.demo.dao.ProductContent;
import com.example.demo.dto.InventoryArticleDTO;
import com.example.demo.repository.ArticleRepository;

/**
 *
 * @author anton.nilsson
 */
@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    public ArticleService(@Autowired ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Saves a new {@link Article} to the database
     * @param articleDTO POJO containing information needed to save the {@link Article}
     * @return The saved {@link Article}
     */
    public Article saveInventoryArticle(InventoryArticleDTO articleDTO) {
        Article article = articleRepository.findById(articleDTO.art_id()).orElse(null);
        if(article != null) {
            article.setStock(article.getStock() + articleDTO.stock());
        } else {
            article = new Article(articleDTO.name(), articleDTO.stock());
        }
        return articleRepository.save(article);
    }

    /**
     * Get all {@link Article}s 
     * @return List of {@link Article}s
     */
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * Get the {@link Article} with the specified articleid
     * @param id The articleid to look for.
     * @return {@link Article} with the given id or null if not found.
     */
    public Article getArticleById(Integer id) {
        return articleRepository.findById(id).orElse(null);
    }

    /**
     * Removes articles from the stock that is needed by the given product.
     * @param product Product from which we select stock from
     * @return The given product
     */
    public Product removeItemsFromStock(Product product) throws SQLException, IllegalArgumentException {
        List<Article> saveList = new ArrayList<>();
        for (ProductContent content : product.getProductContent()) {
            Integer amountNeeded = content.getAmountNeeded();
            if(amountNeeded < 1) {
                throw new IllegalArgumentException("Cannot reduce stock with an amount less than 1");
            }

            if((content.getArticle().getStock() - amountNeeded) < 0) {
                throw new IllegalArgumentException("Reducing stock by " + amountNeeded + " would result in a negative stock" + 
                " for article: " + content.getArticle().getName() + "(" + content.getArticle().getArticleid() + ")");
            }

            content.getArticle().setStock(content.getArticle().getStock() - amountNeeded);
            saveList.add(content.getArticle());
        }

        articleRepository.saveAll(saveList);
        return product;
    }

    /**
     * Checks whether there is stock available for given {@link Product}
     * @param product {@link Product} that we want to check
     * @return true or false
     * @throws SQLException In case we cannot find an article that the product needs
     */
    public boolean checkStockAvailability(Product product) throws SQLException {
        for (ProductContent content : product.getProductContent()) {
            Article article = content.getArticle();
            if (article == null) {
                throw new SQLException("Could not find article in our stock");
            }

            if (article.getStock() < content.getAmountNeeded()) {
                return false;
            }
        }
        return true;
    }
}
