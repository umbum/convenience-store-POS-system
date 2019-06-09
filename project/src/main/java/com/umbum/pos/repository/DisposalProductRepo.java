package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.DisposalProduct;

public interface DisposalProductRepo {
    public List<DisposalProduct> readAll(String date);
    public int create(DisposalProduct disposalProduct);
    public int[] createAll(List<DisposalProduct> disposalProducts);
    public int update(DisposalProduct disposalProduct);
    public int[] updateAll(List<DisposalProduct> disposalProducts);
}
