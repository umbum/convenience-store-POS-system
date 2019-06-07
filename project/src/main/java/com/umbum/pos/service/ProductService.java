package com.umbum.pos.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.Event;
import com.umbum.pos.model.Product;
import com.umbum.pos.repository.EventRepo;
import com.umbum.pos.repository.ProductRepo;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo, EventRepo eventRepo) {
        this.productRepo = productRepo;
    }

    public Product getProduct(long productId) {
        return productRepo.read(productId);
    }
}
