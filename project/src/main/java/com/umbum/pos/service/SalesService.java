package com.umbum.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umbum.pos.model.Sales;
import com.umbum.pos.model.SalesInfo;
import com.umbum.pos.repository.CancelRepo;
import com.umbum.pos.repository.CustomerRepo;
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
    private final CustomerRepo customerRepo;
    private final CancelRepo cancelRepo;

    public SalesService(SalesRepo salesRepo, PaymentRepo paymentRepo, SalesProductRepo salesProductRepo,
        CustomerRepo customerRepo, CancelRepo cancelRepo) {
        this.salesRepo = salesRepo;
        this.paymentRepo = paymentRepo;
        this.salesProductRepo = salesProductRepo;
        this.customerRepo = customerRepo;
        this.cancelRepo = cancelRepo;
    }

    public boolean isValidSalesInfo(SalesInfo salesInfo) {
        return true;
    }

    @Transactional
    public String saveSalesInfo(SalesInfo salesInfo, long branchId) {
        salesInfo.getSales().setBranchId(branchId);
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

        // 고객 정보가 존재하는 경우 mileage 업데이트.
        if (salesInfo.getSales().getCustomerId() != null) {
            customerRepo.update(
                Long.parseLong(salesInfo.getSales().getCustomerId().toString()),
                Integer.parseInt(salesInfo.getSales().getEarnedMileage().toString())
            );
        }

        return "SUCCESS";
    }


    @Transactional
    public List<SalesInfo> getSalesInfos(String date) {
        List<Sales> salesList = salesRepo.readAll(date);
        ArrayList<SalesInfo> salesInfos = new ArrayList<>();
        for (Sales sales : salesList) {
            SalesInfo salesInfo = new SalesInfo();
            salesInfo.setSales(sales);
            salesInfo.setSalesProductList(salesProductRepo.readAll(sales.getSalesId()));
            salesInfo.setPaymentList(paymentRepo.readAll(sales.getSalesId()));
            salesInfos.add(salesInfo);
        }
        return salesInfos;
    }

    public SalesInfo getSalesInfo(long receiptId) {
        return null;
    }


    public String cancelSalesInfo(SalesInfo salesInfo) {
        cancelRepo.create(salesInfo.getSales().getSalesId());
//        customerRepo.update(salesInfo.getSales().getSalesId(), salesInfo.);
        /*
        CUSTOMER 마일리지 회수
        UPDATE CUSTOMER
        SET MIL = MIL- ?(AMOUNT)*0.1
        WHERE CUSTOMER_ID = ?

        판매레코드 CANCEL_CHECK를 변경
        UPDATE SALES
        SET CANCEL_CHECK = 1
        WHERE SALES_ID = ?

        STOCK 레코드 재고 증가
        UPDATE STOCK
        SET QUANTITY = QUANTITY + ?(CANCEL_PRODUCT의 QUANTITY)
        WHERE PRODUCT_ID = ?
         */

        return "SUCCESS";
    }

}
