package com.umbum.pos.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umbum.pos.model.Account;
import com.umbum.pos.model.SalesInfo;
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
    public String saveSalesInfo(SalesInfo salesInfo, Account account) {

        salesInfo.getSales().setBranchId(account.getBranchId());
        long salesId = salesRepo.create(salesInfo.getSales());

        salesInfo.getSalesProductList().forEach(salesProduct -> {
            salesProduct.setSalesId(salesId);
        });
        salesInfo.getPaymentList().forEach(payment -> {
            payment.setSalesId(salesId);
        });

        // 오라클은 update 성공 시 원소가 -2 또는 양수 리턴값을 반환한다. 따라서 int[]는 원소가 -2 또는 양수이고 길이가 update문의 수행 횟수인 배열.
        paymentRepo.createAll(salesInfo.getPaymentList());
        salesProductRepo.createAll(salesInfo.getSalesProductList());

        return "SUCCESS";
    }

}
