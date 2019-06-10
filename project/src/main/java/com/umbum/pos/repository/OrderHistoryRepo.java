package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.Order.OrderHistory;
import com.umbum.pos.model.Order.OrderProduct;

public interface OrderHistoryRepo {
    public OrderHistory read(long orderId);
    public OrderHistory read(long orderId, long companyId);
    public List<OrderHistory> readAll(String date);
    public int create(OrderHistory orderHistory);
    public int update(OrderHistory orderHistory);
}
