package com.umbum.pos.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.Account;
import com.umbum.pos.model.Sales;
import com.umbum.pos.model.SalesInfo;
import com.umbum.pos.service.SalesService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/sales-page")
    public String salesPage() {
        return "sales.html";
    }

    @ResponseBody
    @PostMapping("/sales")
    public String postSales(@RequestBody SalesInfo salesInfo, @AuthenticationPrincipal Account account) {
        if (!salesService.isValidSalesInfo(salesInfo)) {
            return "FAIL";
        }

        return salesService.saveSalesInfo(salesInfo, account.getBranchId());
    }


    @GetMapping("/receipt-page")
    public String receiptPage() {
        return "receipt.html";
    }

    /**
     * 전체 receipt 중에 date로 검색
     */
    @ResponseBody
    @GetMapping("/receipt")
    public List<SalesInfo> getReceipt(@RequestParam String date) {
        return salesService.getSalesInfos(date);
    }

    /**
     * receiptId로 검색
     */
    @ResponseBody
    @GetMapping("/receipt/{receiptId}")
    public SalesInfo getReceipt(@PathVariable long receiptId) {
        return salesService.getSalesInfo(receiptId);
    }

}
