package com.umbum.pos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.umbum.pos.model.Event;
import com.umbum.pos.model.Product;
import com.umbum.pos.service.EventService;
import com.umbum.pos.service.ProductService;

@RestController
public class ProductController {
    private final ProductService productService;
    private final EventService eventService;

    public ProductController(ProductService productService, EventService eventService) {
        this.productService = productService;
        this.eventService = eventService;
    }

    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable("productId") long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/product-with-events/{productId}")
    public Map<String, Object> getProductWithEvents(@PathVariable("productId") long productId) {
        Product product = productService.getProduct(productId);
        List<Event> events = eventService.getEvents(productId);

        Map<String, Object> productWithEvents = new HashMap<>();
        productWithEvents.put("product", product);
        productWithEvents.put("events", events);
        return productWithEvents;
    }
}
