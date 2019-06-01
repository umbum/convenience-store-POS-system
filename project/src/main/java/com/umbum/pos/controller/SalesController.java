package com.umbum.pos.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umbum.pos.model.Payment;
import com.umbum.pos.model.Sales;
import com.umbum.pos.model.SalesProduct;
import com.umbum.pos.service.SalesService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping()
    public String sales() {
        return "sales.html";
    }


    @PostMapping()
    @ResponseBody
    public String postSales(@RequestBody Map<String, Object> salesRequestJson) {
        // 일부러 VO를 안만들었다.
        // SalesRequestPacket이라는 VO를 만들면, 그 VO는 여기에서 밖에 안쓰이고 단순히 Payment, Sales, SalesProduct의 wrapper 역할만 하니까...
        ObjectMapper objectMapper = new ObjectMapper();

        Payment[] payments = objectMapper.convertValue(salesRequestJson.get("payments"), Payment[].class);
        Sales sales = objectMapper.convertValue(salesRequestJson.get("sales"), Sales.class);
        SalesProduct[] salesProducts = objectMapper.convertValue(salesRequestJson.get("sales_products"), SalesProduct[].class);

        System.out.println(sales);
        for (Payment p : payments) {
            System.out.println(p);
        }
        for (SalesProduct s : salesProducts) {
            System.out.println(s);
        }

        return "SUCCESS";
    }
}
