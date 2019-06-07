package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.OrderProduct;

public interface OrderProductRepo {
    public List<OrderProduct> readAll(String date);
    public List<OrderProduct> readAll(long orderId, long companyId);
    public int create(OrderProduct orderProduct);
    public int[] createAll(String date, List<OrderProduct> orderProducts);
    public int update(OrderProduct orderProduct);
    public int[] updateAll(String date, List<OrderProduct> orderProducts);

}
