package com.example.demo.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.TestUtil;
import com.example.demo.dao.Product;
import com.example.demo.dto.ArticleDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.ProductRepository;

/**
 *
 * @author anton.nilsson
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ArticleService articleService;
    @Mock
    private ProductRepository productRepository;

    @Test
    public void testProductsAvailableByStock() {
        Product testProduct = TestUtil.getTestProductA();

        Integer productsAvailable = productService.getProductsAvailableByStock(testProduct);

        assertEquals(1, productsAvailable);
    }

    @Test
    public void testSaveProduct() {
        Product testProduct = TestUtil.getTestProductA();
        ProductDTO testProductDTO = TestUtil.getTestProductDTOA();

        when(articleService.getArticleById(1)).thenReturn(TestUtil.getTestArticleA());
        when(articleService.getArticleById(2)).thenReturn(TestUtil.getTestArticleB());
        when(articleService.getArticleById(3)).thenReturn(TestUtil.getTestArticleC());
        try {
            productService.saveProduct(testProductDTO);
        } catch (Exception e) {
            fail();
        }

        assertEquals(testProduct.getName(), testProductDTO.name());
        assertEquals(testProduct.getPrice(), new BigDecimal(testProductDTO.price()));
        for (ArticleDTO articleDTOs : testProductDTO.contain_articles()) {
            switch (articleDTOs.art_id()) {
                case 1 -> assertEquals(4, articleDTOs.amount_of());
                case 2 -> assertEquals(8, articleDTOs.amount_of());
                case 3 -> assertEquals(1, articleDTOs.amount_of());
                default -> fail();
            }
        }
    }


}
