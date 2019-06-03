package com.umbum.pos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.DiscardHistory;
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
    @GetMapping("/discard")
    public String discard() {
        // 폐기

        return "discard.html";
    }


    @GetMapping("/discard-history/{date}")
    @ResponseBody
    public List<DiscardHistory> getDiscardHistory(@PathVariable String date) {
        List<DiscardHistory> discardHistoryList;
        discardHistoryList = supplyMgntService.getDicardHistories(date);

        return discardHistoryList;
    }

    @PostMapping("/discard-history/{date}")
    @ResponseBody
    public String postDiscardHistory(@PathVariable String date) {
        return "{}";
    }

    /**
     * 입고(재고) 등록 및 조회
     */
    @GetMapping("/in-stock")
    public String inStock() {
        return "in-stock.html";
    }
    /**
     * 오출 등록 및 조회
     */
    @GetMapping("/mis-delivered")
    public String misDelivered() {
        return "mis-delivered.html";
    }
}
