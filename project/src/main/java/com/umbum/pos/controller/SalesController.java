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
import com.umbum.pos.model.SalesInfo;
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
    public String postSales(@RequestBody SalesInfo salesInfo) {
        String result = null;
        if (salesService.isValidSalesInfo(salesInfo)) {
            result = salesService.saveSalesInfo(salesInfo);
        }
        else {
            result = "FAIL";
        }

        return result;
    }
}
