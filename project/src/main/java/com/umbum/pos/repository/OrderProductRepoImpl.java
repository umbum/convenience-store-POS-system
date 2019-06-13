package com.umbum.pos.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.Order.OrderProduct;

@Repository
public class OrderProductRepoImpl implements OrderProductRepo {
    private final JdbcTemplate jdbcTemplate;

    public OrderProductRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(long orderId, OrderProduct orderProduct) {
        return 0;
    }

    @Override
    public int[] createAll(long orderId, List<OrderProduct> orderProduct) {

        String query = "INSERT INTO ORDER_PRODUCT(ORDER_ID, PRODUCT_ID, QUANTITY)\n"
            + "        VALUES(?,?,?)";

        int[] updateCounts = jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, Long.toString(orderId));
                ps.setString(2, Long.toString(orderProduct.get(i).getProductId()));
                ps.setString(3, Integer.toString(orderProduct.get(i).getOrderQuantity()));
            }

            @Override
            public int getBatchSize() {
                return orderProduct.size();
            }
        });

        return updateCounts;
    }

    @Override
    public int update(OrderProduct orderProduct) {
        return 0;
    }
}
