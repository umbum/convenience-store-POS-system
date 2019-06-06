package com.umbum.pos.repository;

import com.umbum.pos.model.Sales;

public interface SalesRepo {
    public Sales read(long salesId);
    public long create(Sales sales);
}
