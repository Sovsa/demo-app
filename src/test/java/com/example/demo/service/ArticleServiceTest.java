package com.example.demo.service;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.demo.TestUtil;
import com.example.demo.dao.Article;
import com.example.demo.dao.Product;
import com.example.demo.dao.ProductContent;
import com.example.demo.dto.InventoryArticleDTO;
import com.example.demo.repository.ArticleRepository;

/**
 *
 * @author anton.nilsson
 */
@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleService articleService;

    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    public void testAddArticleToStock() {
        InventoryArticleDTO articleDTO = TestUtil.getTestInventoryArticleDTOA();
        Article tesArticle = TestUtil.getTestArticleA();

        when(articleRepository.save(any(Article.class))).thenReturn(tesArticle);

        Article articleStock = null;
        try {
            articleStock = articleService.saveInventoryArticle(articleDTO);
        } catch (IllegalArgumentException e) {
            fail();
        }
        
        assertEquals(6, articleStock.getStock());
    }

    @Test
    public void testRemoveItemsFromStock() {
        Product testProduct = TestUtil.getTestProductA();

        Product product = null;
        try {
            product = articleService.removeItemsFromStock(testProduct);
        } catch (SQLException e) {
            fail();
        }

        for (ProductContent content : product.getProductContent()) {
            switch (content.getArticleid()) {
                case 1:
                    assertEquals(content.getArticle().getStock(), 2);
                    break;
                case 2:
                assertEquals(content.getArticle().getStock(), 12);
                    break;
                case 3:
                assertEquals(content.getArticle().getStock(), 1);
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    public void testRemoveStockByLessThanOne() {
        Product testProduct = TestUtil.getTestProductA();
        testProduct.getProductContent().get(0).setAmountNeeded(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> articleService.removeItemsFromStock(testProduct));
        assertEquals("Cannot reduce stock with an amount less than 1", exception.getMessage());
    }

    @Test
    public void testRemoveStockMoreThanAvailable() {
        Product testProduct = TestUtil.getTestProductA();
        testProduct.getProductContent().get(0).setAmountNeeded(99);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> articleService.removeItemsFromStock(testProduct));
        assertTrue(exception.getMessage().startsWith("Reducing stock by"));
    }

    @Test
    public void testCheckStockAvailability() {
        Product testProduct = TestUtil.getTestProductA();

        boolean stockAvailability = false;
        try {
            stockAvailability = articleService.checkStockAvailability(testProduct);
        } catch (SQLException e) {
            fail();
        }
        assertTrue(stockAvailability);
    }
}