package com.example.demo.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.InventoryArticleDTO;
import com.example.demo.dto.InventoryWrapper;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductWrapper;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FileController {

    private final ArticleService articleService;
    private final ProductService productService;

    public FileController(@Autowired ArticleService articleService,
                          @Autowired ProductService productService) {
        this.articleService = articleService;
        this.productService = productService;
    }

    @PostMapping("/upload-product")
    public String handleProductUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please upload a valid product file.");
            return "redirect:/";
        }

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();

            ProductWrapper wrapper = mapper.readValue(inputStream, ProductWrapper.class);
            for (ProductDTO product : wrapper.products()) {
                productService.saveProduct(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error reading product file.");
        }

        redirectAttributes.addFlashAttribute("message", "Products uploaded and parsed successfully!");
        return "redirect:/";
    }

    @PostMapping("/upload-article")
    public String handleArticleUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please upload a valid article file.");
            return "redirect:/";
        }

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            InventoryWrapper wrapper = mapper.readValue(inputStream, InventoryWrapper.class);

            for (InventoryArticleDTO inventory : wrapper.inventory()) {
                articleService.saveInventoryArticle(inventory);
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error reading article file.");
        }

        redirectAttributes.addFlashAttribute("message", "Articles uploaded and parsed successfully!");
        return "redirect:/";
    }
}