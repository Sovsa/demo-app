package com.example.demo.service;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.example.demo.TestUtil;
import com.example.demo.dao.Product;
import com.example.demo.dao.ProductContent;

/**
 *
 * @author anton.nilsson
 */
@ExtendWith(MockitoExtension.class)
public class SalesServiceTest {

    @InjectMocks
    private SalesService salesService;
    @Mock
    private ArticleService articleService;
    @Mock
    private ProductService productService;


    @Test
    @MockitoSettings(strictness = Strictness.WARN)
    public void testBuyProduct() {
        Product testProduct = TestUtil.getTestProductA();
        Integer testArticleAStock = TestUtil.getTestArticleA().getStock();
        Integer testArticleBStock = TestUtil.getTestArticleB().getStock();
        Integer testArticleCStock = TestUtil.getTestArticleC().getStock();

        when(productService.getProductByProductId(testProduct.getProductid())).thenReturn(testProduct);
        try {
            when(articleService.checkStockAvailability(testProduct)).thenReturn(true);
            salesService.buyProduct(testProduct.getProductid());
        } catch (IllegalArgumentException | SQLException e) {
            fail();
        }

        for (ProductContent content : testProduct.getProductContent()) {
            switch (content.getArticleid()) {
                case 1 -> assertEquals(testArticleAStock - content.getAmountNeeded(), content.getArticle().getStock());
                case 2 -> assertEquals(testArticleBStock - content.getAmountNeeded(), content.getArticle().getStock());
                case 3 -> assertEquals(testArticleCStock - content.getAmountNeeded(), content.getArticle().getStock());
                default -> throw new AssertionError();
            }
        }
    }
}
