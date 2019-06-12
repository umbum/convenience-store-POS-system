package com.umbum.pos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.Order.OrderHistory;
import com.umbum.pos.repository.OrderHistoryRepo;

@Service
public class OrderService {
    private final OrderHistoryRepo orderHistoryRepo;

    public OrderService(OrderHistoryRepo orderHistoryRepo) {
        this.orderHistoryRepo = orderHistoryRepo;
    }

    public OrderHistory getOrderHistoryOfCompany(long orderId, long companyId) {
        return orderHistoryRepo.read(orderId, companyId);
    }
    public List<OrderHistory> getOrderHistoryBydate(String date) {
        return orderHistoryRepo.readAll(date);
    }

}
