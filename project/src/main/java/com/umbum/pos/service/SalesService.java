package com.umbum.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umbum.pos.model.Sales;
import com.umbum.pos.model.SalesInfo;
import com.umbum.pos.model.SalesProduct;
import com.umbum.pos.repository.CancelRepo;
import com.umbum.pos.repository.CustomerRepo;
import com.umbum.pos.repository.PaymentRepo;
import com.umbum.pos.repository.ProductInstockHistoryRepo;
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
    private final ProductInstockHistoryRepo productInstockHistoryRepo;

    public SalesService(SalesRepo salesRepo, PaymentRepo paymentRepo, SalesProductRepo salesProductRepo,
        CustomerRepo customerRepo, CancelRepo cancelRepo,
        ProductInstockHistoryRepo productInstockHistoryRepo) {
        this.salesRepo = salesRepo;
        this.paymentRepo = paymentRepo;
        this.salesProductRepo = salesProductRepo;
        this.customerRepo = customerRepo;
        this.cancelRepo = cancelRepo;
        this.productInstockHistoryRepo = productInstockHistoryRepo;
    }

    public boolean isValidSalesInfo(SalesInfo salesInfo) {
        for (SalesProduct salesProduct : salesInfo.getSalesProductList()) {
            if (salesProduct.getQuantity() <= 0) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public int saveSalesInfo(SalesInfo salesInfo, long branchId) {
        salesInfo.getSales().setBranchId(branchId);
        long salesId = salesRepo.create(salesInfo.getSales());

        salesInfo.getSalesProductList().forEach(salesProduct -> {
            salesProduct.setSalesId(salesId);
        });
        salesInfo.getPaymentList().forEach(payment -> {
            payment.setSalesId(salesId);
        });

        // 오라클은 update 성공 시 원소가 -2 또는 양수 리턴값을 반환한다. 따라서 int[]는 원소가 -2 또는 양수이고 길이가 update문의 수행 횟수인 배열.
        int[] updateCountsPayment = paymentRepo.createAll(salesInfo.getPaymentList());
        int[] updateCountsSales = salesProductRepo.createAll(salesInfo.getSalesProductList());

        // 고객 정보가 존재하는 경우 mileage 업데이트.
        if (salesInfo.getSales().getCustomerId() != null) {
            customerRepo.update(
                Long.parseLong(salesInfo.getSales().getCustomerId().toString()),
                Integer.parseInt(salesInfo.getSales().getEarnedMileage().toString())
            );
        }

        return updateCountsPayment.length + updateCountsSales.length;
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

    @Transactional
    public String cancelSalesInfo(SalesInfo salesInfo, long branchId) {
        cancelRepo.create(salesInfo.getSales().getSalesId());

        if (salesInfo.getSales().getEarnedMileage() != null) {
            customerRepo.update(salesInfo.getSales().getSalesId(),  0 - salesInfo.getSales().getEarnedMileage());
        }

        salesRepo.updateCancelCheck(salesInfo.getSales().getSalesId());

        for (SalesProduct salesProduct : salesInfo.getSalesProductList()) {
            productInstockHistoryRepo.updateInstockQuantity(salesProduct.getProductId(), branchId, salesProduct.getQuantity());
        }

        return "SUCCESS";
    }

}
