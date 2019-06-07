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
            + "FROM RECEIVE R, RECEIVE_PRODUCT RP, PRODUCT P, ORDER_PRODUCT OP, A_ORDER O, COMPANY C\n"
            + "WHERE TRUNC(?) = R.RCV_DATE AND R.ORDER_ID = RP.ORDER_ID\n"
            + "AND O.ORDER_ID = R.ORDER_ID AND RP.COMPANY_ID = C.COMPANY_ID\n"
            + "AND RP.PRODUCT_ID = P.PRODUCT_ID AND R.ORDER_ID = OP.ORDER_ID AND RP.PRODUCT_ID = OP.PRODUCT_ID";


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
        // RECEIVE 레코드 생성
        String query = "";
        /**
        RECEIVE 레코드 생성
        INSERT INTO RECEIVE(COMPANY_ID,ORDER_ID,RCV_DATE)
        VALUES(?,?,TRUNC(SYSDATE));

        입고물품에 등록하기 위해 물품 바코드 찍어서 PRODUCT_ID를 들고옴
        SELECT PRODUCT_ID FROM PRODUCT WHERE ? = BARCODE;

        RECEIVE_PRODUCT 레코드 생성
        INSERT INTO RECEIVE_PRODUCT(COMPANY_ID, ORDER_ID, PRODUCT_ID, QUANTITY)
        VALUES(?,?,?,?);

        오차=0 OR 과배송 일경우 STOCK 레코드 생성
        MERGE INTO STOCK S
        USING DUAL ON (S.PRODUCT_ID = ? AND S.BRANCH_ID = ?)
        WHEN MATCHED THEN UPDATE SET S.QUANTITY = S.QUANTITY + ?(발주수량)
        WHEN NOT MATCHED THEN
        INSERT (S.PRODUCT_ID, S.BRANCH_ID, S.QUANTITY)
        VALUES (?,?,?)

        작게 배송일경우 STOCK 레코드
        MERGE INTO STOCK S
        USING DUAL ON (S.PRODUCT_ID = ? AND S.BRANCH_ID = ?)
        WHEN MATCHED THEN UPDATE SET S.QUANTITY = S.QUANTITY + ?(입고수량)
        WHEN NOT MATCHED THEN
        INSERT (S.PRODUCT_ID, S.BRANCH_ID, S.QUANTITY)
        VALUES (?,?,?)

        오배송일경우 SEND_ERROR 레코드 생성
        INSERT INTO SEND_ERROR(COMPANY_ID, ORDER_ID, SENDERR_CODE, SENDERROR_DATE)
        VALUES(?,?,?,TRUNC(SYSDATE));

        SEND_ERROR 레코드 생성후 SENDERROR_PRODUCT 레코드 생성
        INSERT INTO SENDERROR_PRODUCT(COMPANY_ID, ORDER_ID, PRODUCT_ID, SENDERR_CODE, QUANTITY)
        VALUES(?,?,?,?,?(차이수량));
         */




        return 0;
    }
}
