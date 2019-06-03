package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.SalesProduct;

public interface SalesProductRepo {
    public SalesProduct read(long prodCode, long salesNum);
    public void create(SalesProduct salesProduct);
    public void createAll(List<SalesProduct> salesProductList);
}
