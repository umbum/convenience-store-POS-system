package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.SalesProduct;

public interface SalesProductRepo {
    public SalesProduct read(long prodCode, long salesNum);
    public int create(SalesProduct salesProduct);
    public int createAll(List<SalesProduct> salesProductList);
}
