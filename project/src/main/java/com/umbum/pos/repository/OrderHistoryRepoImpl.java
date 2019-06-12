package com.umbum.pos.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.umbum.pos.model.Order.CompanyOrder;
import com.umbum.pos.model.Order.Order;
import com.umbum.pos.model.Order.OrderHistory;
import com.umbum.pos.model.Order.OrderProduct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class OrderHistoryRepoImpl implements OrderHistoryRepo {
    private final JdbcTemplate jdbcTemplate;

    public OrderHistoryRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OrderHistory read(long orderId) {
        return null;
    }

    @Transactional
    @Override
    public OrderHistory read(long orderId, long companyId) {

        // receive check 및 해당 발주-업체번호가 유효한지도 체크
        String receiveCheck = "SELECT S.RCV_CHECK AS RECEIVE_CHECK FROM SEND S WHERE S.ORDER_ID = ? AND S.COMPANY_ID = ?";
        int receiveCheckFlag = 0;
        try {
            receiveCheckFlag = jdbcTemplate.queryForObject(receiveCheck, new Object[]{orderId, companyId}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("OrderHistory({}, {}) there is no such record.", orderId, companyId);
            return null;
        }

        OrderHistory orderHistory = new OrderHistory();
        String selectOrder = "SELECT ORDER_ID, BRANCH_ID, ORD_DATE AS ORDER_DATE, AMOUNT FROM A_ORDER WHERE ORDER_ID = ?";
        orderHistory.setOrder(
            jdbcTemplate.queryForObject(selectOrder, new Object[]{orderId}, new BeanPropertyRowMapper<>(Order.class))
        );

        String selectCompanyOrders = null;
        String selectOrderProduct = null;
        if (receiveCheckFlag == 1) {
            selectCompanyOrders = "SELECT C.COMPANY_ID, C.COM_NAME AS COMPANY_NAME, S.ORDER_ID, S.SEND_DATE, S.RCV_CHECK AS RECEIVE_CHECK, R.RCV_DATE AS RECEIVE_DATE FROM SEND S, COMPANY C, RECEIVE R WHERE S.ORDER_ID = ? AND S.COMPANY_ID = ? AND S.COMPANY_ID = C.COMPANY_ID AND (R.COMPANY_ID = S.COMPANY_ID AND R.ORDER_ID = S.ORDER_ID)";
            selectOrderProduct = "SELECT OP.ORDER_ID, OP.PRODUCT_ID, P.NAME AS PRODUCT_NAME, OP.QUANTITY AS ORDER_QUANTITY, P.ORD_PRI AS ORDER_PRICE, RP.QUANTITY AS RECEIVE_QUANTITY\n"
                + "FROM ORDER_PRODUCT OP, PRODUCT P, RECEIVE_PRODUCT RP, SEND S\n"
                + "WHERE S.ORDER_ID = ? AND S.COMPANY_ID = ? AND OP.ORDER_ID = S.ORDER_ID\n"
                + "AND OP.PRODUCT_ID = P.PRODUCT_ID AND (OP.PRODUCT_ID = RP.PRODUCT_ID AND OP.ORDER_ID = RP.ORDER_ID AND S.COMPANY_ID = RP.COMPANY_ID)";
        }
        else {
            selectCompanyOrders = "SELECT C.COMPANY_ID, C.COM_NAME AS COMPANY_NAME, S.ORDER_ID, S.SEND_DATE, S.RCV_CHECK AS RECEIVE_CHECK FROM SEND S, COMPANY C WHERE S.ORDER_ID = ? AND S.COMPANY_ID = ? AND S.COMPANY_ID = C.COMPANY_ID";
            selectOrderProduct = "SELECT O.ORDER_ID, OP.PRODUCT_ID, P.NAME AS PRODUCT_NAME, OP.QUANTITY AS ORDER_QUANTITY, P.ORD_PRI AS ORDER_PRICE\n"
                + "FROM A_ORDER O , ORDER_PRODUCT OP, PRODUCT P ,COMPANY C, SEND S\n"
                + "WHERE O.ORDER_ID = ? AND C.COMPANY_ID = ? AND S.COMPANY_ID = C.COMPANY_ID AND S.ORDER_ID = O.ORDER_ID\n"
                + "AND O.ORDER_ID = OP.ORDER_ID AND OP.PRODUCT_ID = P.PRODUCT_ID";
        }

        orderHistory.setCompanyOrderList(
            jdbcTemplate.query(selectCompanyOrders, new Object[]{orderId, companyId}, new BeanPropertyRowMapper<>(CompanyOrder.class))
        );

        assert orderHistory.getCompanyOrderList().size() == 1;

        orderHistory.getCompanyOrderList().get(0).setOrderProductList(
            jdbcTemplate.query(selectOrderProduct, new Object[]{orderId, companyId}, new BeanPropertyRowMapper<>(OrderProduct.class))
        );

        return orderHistory;
    }

    @Transactional
    @Override
    public List<OrderHistory> readAll(String date, long branchId) {

        List<OrderHistory> orderHistoryList = new ArrayList<>();
        Date dateDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String selectOrder = "SELECT ORDER_ID, BRANCH_ID, ORD_DATE AS ORDER_DATE, AMOUNT FROM A_ORDER WHERE ORD_DATE = TRUNC(?) AND BRANCH_ID = ?";
        String selectCompanyOrders = "SELECT C.COMPANY_ID, C.COM_NAME AS COMPANY_NAME, S.ORDER_ID, S.SEND_DATE, S.RCV_CHECK AS RECEIVE_CHECK FROM SEND S, COMPANY C WHERE S.ORDER_ID = ? AND S.COMPANY_ID = C.COMPANY_ID";
        String selectOrderProduct = "SELECT O.ORDER_ID, OP.PRODUCT_ID, P.NAME AS PRODUCT_NAME, OP.QUANTITY AS ORDER_QUANTITY, P.ORD_PRI AS ORDER_PRICE\n"
            + "FROM A_ORDER O , ORDER_PRODUCT OP, PRODUCT P ,COMPANY C\n"
            + "WHERE O.ORDER_ID = ? AND C.COMPANY_ID = ? AND C.COMPANY_ID = P.COMPANY_ID\n"
            + "AND O.ORDER_ID = OP.ORDER_ID AND OP.PRODUCT_ID = P.PRODUCT_ID";

        List<Order> orderList = jdbcTemplate.query(selectOrder, new Object[]{dateDate, branchId}, new BeanPropertyRowMapper<>(Order.class));
        for (Order order : orderList) {
            List<CompanyOrder> companyOrderList = jdbcTemplate.query(selectCompanyOrders, new Object[]{order.getOrderId()}, new BeanPropertyRowMapper<>(CompanyOrder.class));
            for (CompanyOrder companyOrder : companyOrderList) {
                companyOrder.setOrderProductList(
                    jdbcTemplate.query(selectOrderProduct, new Object[]{order.getOrderId(), companyOrder.getCompanyId()}, new BeanPropertyRowMapper<>(OrderProduct.class))
                );
            }

            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrder(order);
            orderHistory.setCompanyOrderList(companyOrderList);
            orderHistoryList.add(orderHistory);
        }
        return orderHistoryList;
    }

    @Override
    public int create(OrderHistory orderHistory) {
        return 0;
    }

    @Override
    public int update(OrderHistory orderHistory) {
        return 0;
    }

}
