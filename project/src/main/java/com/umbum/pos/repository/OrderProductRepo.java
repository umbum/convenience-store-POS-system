package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.Order.OrderProduct;

public interface OrderProductRepo {
    public int create(long orderId, OrderProduct orderProduct);
    public int[] createAll(long orderId, List<OrderProduct> orderProduct);
    public int update(OrderProduct orderProduct);
}
