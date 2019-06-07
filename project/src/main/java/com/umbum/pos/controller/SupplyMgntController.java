package com.umbum.pos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.DisposalProduct;
import com.umbum.pos.model.ProductInstockHistory;
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

    @ResponseBody
    @GetMapping("/disposal-history/{date}")
    public List<DisposalProduct> getDisposalProducts(@PathVariable String date) {
        if (date.equals("0"))
            return new ArrayList<>();
        return supplyMgntService.getDisposalProducts(date);
    }

    @ResponseBody
    @PostMapping("/disposal-history/{date}")
    public String postDisposalHistories(@PathVariable String date,
        @RequestBody Map<String, List<DisposalProduct>> disposalHistoryChanges) {
//        System.out.println(disposalHistoryChanges.get("update"));
//        System.out.println(disposalHistoryChanges.get("create"));
        supplyMgntService.applyDisposalProductsChange(date, disposalHistoryChanges);

        return "{}";
    }

    /**
     * 입고(재고) / 오출 등록 및 조회
     */
    @GetMapping("/instock")
    public String instock() {
        return "instock.html";
    }

    @ResponseBody
    @GetMapping("/instock/{date}")
    public List<ProductInstockHistory> getInstock(@PathVariable String date) {
        return supplyMgntService.getInstockHistories(date);
    }

    @ResponseBody
    @PostMapping("/instock/{date}")
    public String postInstock() {
        return "instock.html";
    }
}
