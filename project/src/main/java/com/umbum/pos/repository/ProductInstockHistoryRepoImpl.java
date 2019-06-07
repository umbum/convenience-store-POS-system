package com.umbum.pos.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.ProductInstockHistory;

@Repository
public class ProductInstockHistoryRepoImpl implements ProductInstockHistoryRepo {
    private final JdbcTemplate jdbcTemplate;

    public ProductInstockHistoryRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductInstockHistory> readAll(String date) {
        String query = "SELECT "
            + "R.RCV_DATE AS RECEIVE_DATE, O.ORD_DATE AS ORDER_DATE, "
            + "P.NAME AS PRODUCT_NAME, C.COM_NAME AS COMPANY_NAME, "
            + "RP.QUANTITY AS RECEIVE_QUANTITY, "
            + "OP.QUANTITY AS ORDER_QUANTITY\n"
            + "FROM RECEIVE R, A_ORDER O, RECEIVE_PRODUCT RP, PRODUCT P, COMPANY C ,ORDER_PRODUCT OP\n"
            + "WHERE TRUNC(?) = R.RCV_DATE AND R.ORDER_ID = O.ORDER_ID AND R.ORDER_ID = RP.ORDER_ID AND RP.PRODUCT_ID = P.PRODUCT_ID \n"
            + "AND R.COMPANY_ID = C.COMPANY_ID AND O.ORDER_ID = OP.ORDER_ID";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date dateDate = dateFormat.parse(date);
            return jdbcTemplate.query(query, new Object[]{dateDate}, new BeanPropertyRowMapper<>(ProductInstockHistory.class));
        } catch (ParseException e) {
            // 잘못된 날짜 형식이 들어왔을 때.
            throw new RuntimeException(e);
        }
    }

    @Override
    public int create(ProductInstockHistory account) {
        return 0;
    }
}
