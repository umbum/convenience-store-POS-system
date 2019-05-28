package com.umbum.pos.service;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.Product;
import com.umbum.pos.repository.ProductRepo;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product getProduct(long productCode) {
        return productRepo.getProduct(productCode);
    }
}
