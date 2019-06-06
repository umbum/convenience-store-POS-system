package com.umbum.pos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.DisposalHistory;
import com.umbum.pos.service.SupplyMgntService;

@Controller
public class SupplyMgntController {
    private final SupplyMgntService supplyMgntService;

    public SupplyMgntController(SupplyMgntService supplyMgntService) {
        this.supplyMgntService = supplyMgntService;
    }

    /**
     * 폐기 등록 및 조회
     */
    @GetMapping("/disposal")
    public String disposal() {
        return "disposal.html";
    }


    @GetMapping("/disposal-history/{date}")
    @ResponseBody
    public List<DisposalHistory> getDisposalHistory(@PathVariable String date) {
        List<DisposalHistory> disposalHistoryList;
        disposalHistoryList = supplyMgntService.getDisposalHistory(date);

        return disposalHistoryList;
    }

    @PostMapping("/disposal-history/{date}")
    @ResponseBody
    public String postDisposalHistory(@PathVariable String date) {
        return "{}";
    }

    /**
     * 입고(재고) / 오출 등록 및 조회
     */
    @GetMapping("/instock")
    public String inStock() {
        return "instock.html";
    }
}
