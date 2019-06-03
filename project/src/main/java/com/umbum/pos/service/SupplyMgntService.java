package com.umbum.pos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.DiscardHistory;

@Service
public class SupplyMgntService {

    public List<DiscardHistory> getDicardHistories(String date) {
        List<DiscardHistory> discardHistoryList = new ArrayList<DiscardHistory>();
        if (date.equals("20190603")) {
            for (int i = 0; i < 10; i++) {
                discardHistoryList.add(new DiscardHistory(i, "테스트", 1, "20190603"));
            }
        }
        else if (date.equals("20190602")) {
            discardHistoryList.add(new DiscardHistory(1, "테스트", 1, "20190602"));
        }
        return discardHistoryList;
    }

}
