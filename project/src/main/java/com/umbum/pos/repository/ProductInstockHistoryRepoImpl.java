package com.umbum.pos.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ProductInstockHistory> readAll(long orderId, long companyId) {
        String query = "SELECT O.ORDER_ID, O.ORD_DATE AS ORDER_DATE, OP.PRODUCT_ID, P.NAME AS PRODUCT_NAME, OP.QUANTITY AS ORDER_QUANTITY, C.COMPANY_ID, C.COM_NAME AS COMPANY_NAME\n"
            + "FROM A_ORDER O , ORDER_PRODUCT OP, PRODUCT P ,COMPANY C, SEND S\n"
            + "WHERE O.ORDER_ID = ? AND C.COMPANY_ID = ? AND S.COMPANY_ID = C.COMPANY_ID AND S.ORDER_ID = O.ORDER_ID\n"
            + "AND O.ORDER_ID = OP.ORDER_ID AND OP.PRODUCT_ID = P.PRODUCT_ID";

        return jdbcTemplate.query(query, new Object[]{orderId, companyId}, new BeanPropertyRowMapper<>(ProductInstockHistory.class));
    }

    /**
     * RECEIVE(+_PRODUCT), SEND_ERROR 대상으로 레코드 삽입, STOCK 대상으로 레코드 삽입 또는 수정(수량)
     * @param productInstockHistories
     * @return
     */
    @Transactional
    @Override
    public int createAll(List<ProductInstockHistory> productInstockHistories, long branchId) {

        // INSERT INTO RECEIVE
        String insertIntoReceive = "INSERT INTO RECEIVE(COMPANY_ID,ORDER_ID,RCV_DATE)\n"
            + "VALUES(?,?,TRUNC(SYSDATE))";
        jdbcTemplate.update(insertIntoReceive,
            productInstockHistories.get(0).getCompanyId(),
            productInstockHistories.get(0).getOrderId()
        );

        // INSERT INTO RECEIVE_PRODUCT
        String insertIntoReceiveProduct = "INSERT INTO RECEIVE_PRODUCT(COMPANY_ID, ORDER_ID, PRODUCT_ID, QUANTITY)\n"
            + "VALUES(?,?,?,?)";
        jdbcTemplate.batchUpdate(insertIntoReceiveProduct, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, Long.toString(productInstockHistories.get(i).getCompanyId()));
                ps.setString(2, Long.toString(productInstockHistories.get(i).getOrderId()));
                ps.setString(3, Long.toString(productInstockHistories.get(i).getProductId()));
                ps.setString(4, Integer.toString(productInstockHistories.get(i).getReceiveQuantity()));
            }

            @Override
            public int getBatchSize() {
                return productInstockHistories.size();
            }
        });



        String insertOrUpdateIntoStock = "MERGE INTO STOCK S\n"
            + "USING DUAL ON (S.PRODUCT_ID = ? AND S.BRANCH_ID = ?)\n"
            + "WHEN MATCHED THEN UPDATE SET S.QUANTITY = S.QUANTITY + ?\n"
            + "WHEN NOT MATCHED THEN\n"
            + "INSERT (S.PRODUCT_ID, S.BRANCH_ID, S.QUANTITY)\n"
            + "VALUES (?,?,?)";
        jdbcTemplate.batchUpdate(insertOrUpdateIntoStock, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ProductInstockHistory p = productInstockHistories.get(i);

                // 입고 = 발주 : 아무거나 STOCK에.
                // 입고 > 발주 : 발주 수량 만큼만 STOCK에 넣고 차이 만큼 SEND_ERROR에.
                // 입고 < 발주 : 입고 수량 만큼 STOCK에.
                int quantity = (p.getReceiveQuantity() <= p.getOrderQuantity()) ?
                    p.getReceiveQuantity() : p.getOrderQuantity();

                ps.setString(1, Long.toString(p.getProductId()));
                ps.setString(2, Long.toString(branchId));
                ps.setString(3, Integer.toString(quantity));
                ps.setString(4, Long.toString(p.getProductId()));
                ps.setString(5, Long.toString(branchId));
                ps.setString(6, Integer.toString(quantity));
            }

            @Override
            public int getBatchSize() {
                return productInstockHistories.size();
            }
        });

        // 입고 > 발주 : 발주 수량 만큼만 STOCK에 넣고 차이 만큼 SEND_ERROR에.
//        String insertIntoSendError = "INSERT INTO SEND_ERROR(COMPANY_ID, ORDER_ID, SENDERR_CODE, SENDERROR_DATE)\n"
//            + "VALUES(?,?,?,TRUNC(SYSDATE))";
//        jdbcTemplate.update(insertIntoReceive,
//            productInstockHistories.get(0).getCompanyId(),
//            productInstockHistories.get(0).getOrderId()
//        );
//
//
//        String insertIntoSendErrorProduct = "INSERT INTO SENDERROR_PRODUCT(COMPANY_ID, ORDER_ID, PRODUCT_ID, SENDERR_CODE, QUANTITY)\n"
//            + "VALUES(?,?,?,?,?(차이수량));";


        return 0;
    }
}
