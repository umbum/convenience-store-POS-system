package com.umbum.pos.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.umbum.pos.model.SalesProduct;

@Repository
public class SalesProductRepoImpl implements SalesProductRepo {
    @Override
    public SalesProduct read(long prodCode, long salesNum) {
        return null;
    }

    @Override
    public int create(SalesProduct salesProduct) {
        return 0;
    }

    @Override
    public int createAll(List<SalesProduct> salesProductList) {
        return 0;
    }
}
