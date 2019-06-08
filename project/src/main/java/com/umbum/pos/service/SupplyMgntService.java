package com.umbum.pos.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.DisposalProduct;
import com.umbum.pos.model.ProductInstockHistory;
import com.umbum.pos.repository.DisposalProductRepo;
import com.umbum.pos.repository.ProductInstockHistoryRepo;

@Service
public class SupplyMgntService {
    private final DisposalProductRepo disposalProductRepo;
    private final ProductInstockHistoryRepo productInstockHistoryRepo;

    public SupplyMgntService(DisposalProductRepo disposalProductRepo,
        ProductInstockHistoryRepo productInstockHistoryRepo) {
        this.disposalProductRepo = disposalProductRepo;
        this.productInstockHistoryRepo = productInstockHistoryRepo;
    }

    /**
     * 폐기 등록 및 조회
     */
    public List<DisposalProduct> getDisposalProducts(String date) {
        return disposalProductRepo.readAll(date);
    }

    /**
     *
     * @param date
     *      지금은 폐기 검색이 하루 단위라 필요 없을지 몰라도, 추후 범위 검색 등에 필요할 수 있다.
     * @param updateList
     * @param createList
     * @return
     */
    public int[] applyDisposalProductsChange(String date, List<DisposalProduct> createList, List<DisposalProduct> updateList) {

        if (createList != null && createList.size() != 0)
            disposalProductRepo.createAll(date, createList);
        if (updateList != null && updateList.size() != 0)
            disposalProductRepo.updateAll(date, updateList);

        return new int[0];
    }


    /**
     * 입고(재고) / 오출 등록 및 조회
     */
    public List<ProductInstockHistory> getInstockHistories(String date) {
        return productInstockHistoryRepo.readAll(date);
    }

    public int[] processProductInstockHistories(List<ProductInstockHistory> histories, long branchId) {
        if (histories != null && histories.size() != 0) {
            productInstockHistoryRepo.createAll(histories, branchId);
        }
        return null;
    }
}
