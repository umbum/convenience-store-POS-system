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

import com.umbum.pos.model.DisposalProduct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DisposalProductRepoImpl implements DisposalProductRepo {

    private final JdbcTemplate jdbcTemplate;

    public DisposalProductRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DisposalProduct> readAll(String dateStr) {
        String query = "SELECT DP.PRODUCT_ID, P.NAME AS PRODUCT_NAME, DP.DISPOSAL_ID, DP.QUANTITY, D.DIS_DATE\n"
            + "FROM DISPOSAL_PRODUCT DP, DISPOSAL D, PRODUCT P\n"
            + "WHERE d.dis_date = trunc(?) AND d.disposal_id = dp.disposal_id AND dp.product_id = p.product_id";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date dateDate = dateFormat.parse(dateStr);
            return jdbcTemplate.query(query, new Object[]{dateDate}, new BeanPropertyRowMapper<>(DisposalProduct.class));
        } catch (ParseException e) {
            // 잘못된 날짜 형식이 들어왔을 때.
            throw new RuntimeException(e);
        }
    }

    @Override
    public int create(DisposalProduct payment) {
        return 0;
    }


    @Transactional
    @Override
    public int[] createAll(List<DisposalProduct> disposalProducts) {
        /*
         * "저장" 버튼을 누르는 것을 하나의 폐기 묶음 단위로 본다.
         */
        Date dateDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateDate = dateFormat.parse(disposalProducts.get(0).getDisDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String increaseDisposalId = "INSERT INTO DISPOSAL(DISPOSAL_ID,DIS_DATE)\n"
            + "VALUES(DISPOSAL_ID_SEQ.NEXTVAL,\n"
            + "TRUNC(?))";
        jdbcTemplate.update(increaseDisposalId, dateDate);

        String query = "INSERT INTO DISPOSAL_PRODUCT (PRODUCT_ID,DISPOSAL_ID,QUANTITY)\n"
            + "VALUES(?,DISPOSAL_ID_SEQ.CURRVAL,?)";

        int[] updateCounts = jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, Long.toString(disposalProducts.get(i).getProductId()));
                ps.setString(2, Integer.toString(disposalProducts.get(i).getQuantity()));
            }

            @Override
            public int getBatchSize() {
                return disposalProducts.size();
            }
        });

        return updateCounts;
    }

    @Override
    public int update(DisposalProduct disposalProduct) {
        return 0;
    }

    @Override
    public int[] updateAll(List<DisposalProduct> disposalProducts) {
        // 폐기 상품에 대한 수량 변경(update)는 지금은 고려하지 않는다.
        return new int[0];
    }

}
