package com.umbum.pos.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.umbum.pos.model.Order.Order;

@Repository
public class OrderRepoImpl implements OrderRepo {
    private final JdbcTemplate jdbcTemplate;

    public OrderRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Order create(long branchId, int amount) {
        Order order = new Order();
        order.setOrderId(
            jdbcTemplate.queryForObject("SELECT order_id_seq.NEXTVAL FROM DUAL", Long.class)
        );
        order.setBranchId(branchId);
        order.setAmount(amount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        order.setOrderDate(dateFormat.format(Calendar.getInstance().getTime()));
        Date dateDate = null;
        try {
            dateDate = dateFormat.parse(order.getOrderDate());
        } catch (ParseException e) {
            // 잘못된 날짜 형식이 들어왔을 때.
            throw new RuntimeException(e);
        }
        String query = "INSERT INTO A_ORDER(ORDER_ID, BRANCH_ID, ORD_DATE, AMOUNT)\n"
            + "        VALUES(?,?,TRUNC(?),?)";
        jdbcTemplate.update(query,
            order.getOrderId(),
            order.getBranchId(),
            dateDate,
            order.getAmount()
        );

        return order;
    }

}
