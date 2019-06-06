package com.umbum.pos.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.Account;
import com.umbum.pos.model.SalesInfo;
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
    public String postSales(@RequestBody SalesInfo salesInfo, @AuthenticationPrincipal Account account) {
        if (!salesService.isValidSalesInfo(salesInfo)) {
            return "FAIL";
        }

        return salesService.saveSalesInfo(salesInfo, account);
    }
}
