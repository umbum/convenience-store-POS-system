package com.umbum.pos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.Order.OrderHistory;
import com.umbum.pos.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order-page")
    public String orderPage() {
        return "order.html";
    }

    @ResponseBody
    @GetMapping("/order")
    public List<OrderHistory> getOrderHistories(@RequestParam String date) {
        return orderService.getOrderHistoryBydate(date);
    }

    @ResponseBody
    @GetMapping("/order/{orderId}")
    public OrderHistory getOrderHistories(@PathVariable long orderId) {
        if (orderId == 0)
            return new OrderHistory();
        // 여기는 그냥 orderId로 검색하는거지.
        return new OrderHistory();
    }

    /**
     * 물품이 입고될 때, 발주 내역서가 물품과 함께 들어오며 연관된 ORDER_ID, COMPANY_ID가 내역서에 적혀있다.
     */
    @ResponseBody
    @GetMapping("/order/{orderId}/company/{companyId}")
    public OrderHistory getOrderHistories(@PathVariable long orderId,
        @PathVariable long companyId) {
        // data table에 들어갈거라 null을 리턴할 수 없다. 그래서 빈 객체를 만들어서 리턴해준다. ResponseEntity로 400 내려주는 것도 불가. 무조건 200을 내려줘야 한다.
        OrderHistory orderHistory = orderService.getOrderHistoryOfCompany(orderId, companyId);
        return (orderHistory != null) ? orderHistory : new OrderHistory();
    }


}
