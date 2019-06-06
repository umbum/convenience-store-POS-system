package com.umbum.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.DisposalHistory;

@Service
public class SupplyMgntService {

    public List<DisposalHistory> getDisposalHistory(String date) {
        List<DisposalHistory> disposalHistoryList = new ArrayList<DisposalHistory>();
        if (date.equals("20190603")) {
            for (int i = 0; i < 10; i++) {
                disposalHistoryList.add(new DisposalHistory(i, "테스트", 1, "20190603"));
            }
        }
        else if (date.equals("20190602")) {
            disposalHistoryList.add(new DisposalHistory(1, "테스트", 1, "20190602"));
        }
        return disposalHistoryList;
    }

}
