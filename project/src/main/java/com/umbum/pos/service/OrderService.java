package com.umbum.pos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.Order.Order;
import com.umbum.pos.model.Order.OrderHistory;
import com.umbum.pos.model.Order.OrderProduct;
import com.umbum.pos.repository.OrderHistoryRepo;
import com.umbum.pos.repository.OrderProductRepo;
import com.umbum.pos.repository.OrderRepo;

@Service
public class OrderService {
    private final OrderHistoryRepo orderHistoryRepo;
    private final OrderProductRepo orderProductRepo;
    private final OrderRepo orderRepo;

    public OrderService(OrderHistoryRepo orderHistoryRepo, OrderProductRepo orderProductRepo,
        OrderRepo orderRepo) {
        this.orderHistoryRepo = orderHistoryRepo;
        this.orderProductRepo = orderProductRepo;
        this.orderRepo = orderRepo;
    }

    public OrderHistory getOrderHistoryOfCompany(long orderId, long companyId) {
        return orderHistoryRepo.read(orderId, companyId);
    }
    public List<OrderHistory> getOrderHistoryBydate(String date, long branchId) {
        return orderHistoryRepo.readAll(date, branchId);
    }

    public boolean isValidOrderProduct(List<OrderProduct> orderProducts) {
        if (orderProducts.size() == 0) {
            return false;
        }

        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getOrderQuantity() <= 0) {
                return false;
            }
        }
        return true;
    }

    public int saveOrderProducts(List<OrderProduct> orderProducts, long branchId) {
        int amount = 0;
        for (OrderProduct orderProduct : orderProducts) {
            amount += orderProduct.getOrderPrice() * orderProduct.getOrderQuantity();
        }
        Order order = orderRepo.create(branchId, amount);
        int[] updateCounts = orderProductRepo.createAll(order.getOrderId(), orderProducts);
        return updateCounts.length;
    }

}
