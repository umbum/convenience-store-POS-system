package com.umbum.pos.repository;

import java.text.ParseException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.OrderProduct;

@Repository
public class OrderProductRepoImpl implements OrderProductRepo {
    private final JdbcTemplate jdbcTemplate;

    public OrderProductRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderProduct> readAll(String date) {
        return null;
    }

    @Override
    public List<OrderProduct> readAll(long orderId, long companyId) {
        String query = "SELECT O.ORDER_ID, O.ORD_DATE AS ORDER_DATE, OP.PRODUCT_ID, P.NAME AS PRODUCT_NAME, OP.QUANTITY AS ORDER_QUANTITY, C.COMPANY_ID, C.COM_NAME AS COMPANY_NAME\n"
            + "FROM A_ORDER O , ORDER_PRODUCT OP, PRODUCT P ,COMPANY C, SEND S\n"
            + "WHERE O.ORDER_ID = ? AND C.COMPANY_ID = ? AND S.COMPANY_ID = C.COMPANY_ID AND S.ORDER_ID = O.ORDER_ID\n"
            + "AND O.ORDER_ID = OP.ORDER_ID AND OP.PRODUCT_ID = P.PRODUCT_ID";

        return jdbcTemplate.query(query, new Object[]{orderId, companyId}, new BeanPropertyRowMapper<>(OrderProduct.class));
    }

    @Override
    public int create(OrderProduct orderProduct) {
        return 0;
    }

    @Override
    public int[] createAll(String date, List<OrderProduct> orderProducts) {
        return new int[0];
    }

    @Override
    public int update(OrderProduct orderProduct) {
        return 0;
    }

    @Override
    public int[] updateAll(String date, List<OrderProduct> orderProducts) {
        return new int[0];
    }
}
