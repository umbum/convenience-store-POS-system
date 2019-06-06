package com.umbum.pos.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umbum.pos.model.Product;
import com.umbum.pos.service.ProductService;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/product/{product_code}")
    public Product sendProduct(@PathVariable("product_code") long product_code) {
        return productService.getProduct(product_code);
    }
}
