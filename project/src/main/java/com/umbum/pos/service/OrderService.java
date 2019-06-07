package com.umbum.pos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.OrderProduct;
import com.umbum.pos.repository.OrderProductRepo;

@Service
public class OrderService {
    private final OrderProductRepo orderProductRepo;

    public OrderService(OrderProductRepo orderProductRepo) {
        this.orderProductRepo = orderProductRepo;
    }

    public List<OrderProduct> getOrderProductsFromCompany(long orderId, long companyId) {
        return orderProductRepo.readAll(orderId, companyId);
    }
}
