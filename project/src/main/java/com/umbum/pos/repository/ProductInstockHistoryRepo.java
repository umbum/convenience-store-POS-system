package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.ProductInstockHistory;

public interface ProductInstockHistoryRepo {
    public List<ProductInstockHistory> readAll(String date);
    public List<ProductInstockHistory> readAll(long orderId, long companyId);
    public int createAll(List<ProductInstockHistory> productInstockHistories, long branchId);
}
