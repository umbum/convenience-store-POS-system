package com.umbum.pos.repository;

import com.umbum.pos.model.Sales;

public interface SalesRepo {
    public Sales read();
    public long create(Sales sales);
}
