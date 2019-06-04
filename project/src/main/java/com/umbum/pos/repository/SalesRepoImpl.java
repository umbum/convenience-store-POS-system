package com.umbum.pos.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.umbum.pos.model.Sales;

@Repository
public class SalesRepoImpl implements SalesRepo {

    @Override
    public Sales read() {
        String query = "SELECT * FROM SALES WHERE SALES_NUM = ?";
        return null;
    }

    @Override
    public int create(Sales sales) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        sales.setSalesTime(dateFormat.format(new Date()));

        String query = "INSERT";

        return 123;
    }
}
