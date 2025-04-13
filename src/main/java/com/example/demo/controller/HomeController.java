/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.ArticleModel;
import com.example.demo.dto.ProductModel;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ProductService;

/**
 *
 * @author anton.nilsson
 */
@Controller
public class HomeController {

    private final ArticleService articleService;
    private final ProductService productService;

    public HomeController(@Autowired ArticleService articleService,
                          @Autowired ProductService productService) {
        this.articleService = articleService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String homeController(Model model) {
        List<ArticleModel> articleModels = articleService.getAllArticles().stream()
            .map(article -> new ArticleModel(article.getName(), article.getStock()))
            .collect(Collectors.toList());
        List<ProductModel> productModels = productService.getAllProducts().stream()
            .map(product -> new ProductModel(product, productService.getProductsAvailableByStock(product)))
            .collect(Collectors.toList());

        model.addAttribute("inventoryList", articleModels);
        model.addAttribute("productList", productModels);

        return "index";
    }
}
