package com.umbum.pos.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.DisposalProduct;
import com.umbum.pos.repository.DisposalProductRepo;

@Service
public class SupplyMgntService {
    private final DisposalProductRepo disposalProductRepo;

    public SupplyMgntService(DisposalProductRepo disposalProductRepo) {
        this.disposalProductRepo = disposalProductRepo;
    }

    public List<DisposalProduct> getDisposalProducts(String date) {
        return disposalProductRepo.readAll(date);
    }

    /**
     *
     * @param date
     *  지금은 폐기 검색이 하루 단위라 필요 없을지 몰라도, 추후 범위 검색 등에 필요할 수 있다.
     * @param disposalHistoryChanges
     * @return
     */
    public int[] applyDisposalProductsChange(String date, Map<String, List<DisposalProduct>> disposalHistoryChanges) {

        if (disposalHistoryChanges.get("create").size() != 0)
            disposalProductRepo.createAll(date, disposalHistoryChanges.get("create"));
        if (disposalHistoryChanges.get("update").size() != 0)
            disposalProductRepo.updateAll(date, disposalHistoryChanges.get("update"));

        return new int[0];
    }

}
