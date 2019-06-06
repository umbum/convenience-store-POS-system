package com.umbum.pos.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.Payment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PaymentRepoImpl implements PaymentRepo{
    private final JdbcTemplate jdbcTemplate;

    public PaymentRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Payment read() {
        return null;
    }

    @Override
    public int create(Payment payment) {
        return 0;
    }

    @Override
    public int[] createAll(List<Payment> paymentList) {

        String query = "INSERT INTO PAYMENT(SALES_ID, PAYMENT_CODE, AMOUNT) "
            + "VALUES(?, ?, ?)";

        int[] updateCounts = jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, Long.toString(paymentList.get(i).getSalesId()));
                ps.setString(2, Integer.toString(paymentList.get(i).getPaymentCode().getCode()));
                ps.setString(3, Long.toString(paymentList.get(i).getAmount()));
            }

            @Override
            public int getBatchSize() {
                return paymentList.size();
            }
        });

        return updateCounts;
    }
}
