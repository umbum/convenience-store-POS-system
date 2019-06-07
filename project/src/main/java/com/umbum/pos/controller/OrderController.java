package com.umbum.pos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.OrderProduct;
import com.umbum.pos.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String order() {
        return "order.html";
    }

    @ResponseBody
    @GetMapping("/order/{orderId}")
    public List<OrderProduct> getOrderProducts(@PathVariable long orderId,
        @RequestParam(required = false) Long companyId) {
        if (orderId == 0)
            return new ArrayList<>();

        if (companyId != null) {
            return orderService.getOrderProductsFromCompany(orderId, companyId);
        }

        // 여기는 그냥 orderId로 검색하는거지뭐?? 아님 기간이나..
        return new ArrayList<>();
    }
}
