package com.umbum.pos.repository;

import com.umbum.pos.model.Order.Order;

public interface OrderRepo {
    /**
     * @return Order
     */
    public Order create(long branchId, int amount);
}
