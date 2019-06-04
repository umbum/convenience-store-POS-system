package com.umbum.pos.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public String saveSalesInfo(SalesInfo salesInfo) {
//        salesInfo.getSales().setReceiptId(); 영수증 번호는 어떻게 할거야?

        long salesId = salesRepo.create(salesInfo.getSales());

        salesInfo.getSalesProductList().forEach(salesProduct -> {
            salesProduct.setSalesId(salesId);
        });
        salesInfo.getPaymentList().forEach(payment -> {
            payment.setSalesId(salesId);
        });

//        System.out.println(salesInfo.getSales());
//        for (Payment p : salesInfo.getPaymentList()) {
//            System.out.println(p);
//        }
//        for (SalesProduct s : salesInfo.getSalesProductList()) {
//            System.out.println(s);
//        }

        paymentRepo.createAll(salesInfo.getPaymentList());
        salesProductRepo.createAll(salesInfo.getSalesProductList());

        String result = "SUCCESS";

        return result;
    }

}
