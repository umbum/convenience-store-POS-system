package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.Sales;

public interface SalesRepo {
    public Sales read(long salesId);
    public Sales readByReceiptId(long receiptId);
    public List<Sales> readAll(String date);
    public long create(Sales sales);
    public int updateCancelCheck(long salesId);
}
