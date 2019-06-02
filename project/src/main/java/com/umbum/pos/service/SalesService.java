package com.umbum.pos.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.Payment;
import com.umbum.pos.model.SalesInfo;
import com.umbum.pos.model.SalesProduct;
import com.umbum.pos.repository.PaymentRepo;
import com.umbum.pos.repository.SalesProductRepo;
import com.umbum.pos.repository.SalesRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesService {
    private final SalesRepo salesRepo;
    private final PaymentRepo paymentRepo;
    private final SalesProductRepo salesProductRepo;

    public SalesService(SalesRepo salesRepo, PaymentRepo paymentRepo, SalesProductRepo salesProductRepo) {
        this.salesRepo = salesRepo;
        this.paymentRepo = paymentRepo;
        this.salesProductRepo = salesProductRepo;
    }

    public boolean isValidSalesInfo(SalesInfo salesInfo) {
        return true;
    }



    public String saveSalesInfo(SalesInfo salesInfo) {
//        salesInfo.getSales().setRecNum(); 영수증 번호는 어떻게 할거야?

        // 얘네 전체를 트랜잭션으로 묶고 싶다 이거지...

        long salesNum = salesRepo.create(salesInfo.getSales());

        salesInfo.getSalesProducts().forEach(salesProduct -> {
            salesProduct.setSalesNum(salesNum);
        });
        salesInfo.getPayments().forEach(payment -> {
            payment.setSalesNum(salesNum);
        });

        System.out.println(salesInfo.getSales());
        for (Payment p : salesInfo.getPayments()) {
            System.out.println(p);
        }
        for (SalesProduct s : salesInfo.getSalesProducts()) {
            System.out.println(s);
        }

        paymentRepo.createAll(salesInfo.getPayments());
        salesProductRepo.createAll(salesInfo.getSalesProducts());

        String result = "SUCCESS";

        return result;
    }

}
