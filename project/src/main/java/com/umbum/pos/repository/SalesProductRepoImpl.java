package com.umbum.pos.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.SalesProduct;

@Repository
public class SalesProductRepoImpl implements SalesProductRepo {
    private final JdbcTemplate jdbcTemplate;

    public SalesProductRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SalesProduct read(long prodCode, long salesNum) {
        return null;
    }

    @Override
    public int create(SalesProduct salesProduct) {
        return 0;
    }

    @Override
    public int[] createAll(List<SalesProduct> salesProductList) {

        String query = "INSERT INTO SALES_PRODUCT(SALES_ID, PRODUCT_ID, QUANTITY) "
            + "VALUES(?, ?, ?)";

        int[] updateCounts = jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, Long.toString(salesProductList.get(i).getSalesId()));
                ps.setString(2, Long.toString(salesProductList.get(i).getProductId()));
                ps.setString(3, Integer.toString(salesProductList.get(i).getQuantity()));
            }

            @Override
            public int getBatchSize() {
                return salesProductList.size();
            }
        });

        return updateCounts;
    }
}
