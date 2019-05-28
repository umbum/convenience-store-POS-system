package com.umbum.pos.repository;

import com.umbum.pos.model.Product;

public interface ProductRepo {
    public Product getProduct(long productCode);
}
