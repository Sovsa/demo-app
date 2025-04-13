/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.demo.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.Product;
import com.example.demo.service.SalesService;

/**
 *
 * @author anton.nilsson
 */
@Controller
public class ProductController {

    private final SalesService salesService;

    public ProductController(@Autowired SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping("/buy/{id}")
    public String buyProduct(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Product product = null;
            try {
                product = salesService.buyProduct(id);
            } catch (IllegalArgumentException | SQLException e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                e.printStackTrace();
            }
            redirectAttributes.addFlashAttribute("message", "Successfully purchased " + product.getName());
        return "redirect:/";
    }
}
