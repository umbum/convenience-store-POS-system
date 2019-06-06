package com.umbum.pos.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CustomerRepoImpl implements CustomerRepo {
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer read(String phone) {
        String query = "SELECT CUSTOMER_ID, CUS_NAME, SEX, BIRTH, PHONE, MIL as MILEAGE FROM CUSTOMER WHERE PHONE=?";

        Customer customer = null;
        try {
            customer = jdbcTemplate.queryForObject(query, new Object[]{phone}, new BeanPropertyRowMapper<>(Customer.class));
        } catch (EmptyResultDataAccessException e) {
            log.debug("Incorrect result size: expected 1, actual 0");
        }
        return customer;
    }

    @Override
    public int create(Customer customer) {
        return 0;
    }

    @Override
    public int update(long customerId, long mileage) {
        String query = "UPDATE CUSTOMER SET MIL = MIL + ? WHERE CUSTOMER_ID = ?";
        return jdbcTemplate.update(query, mileage, customerId);
    }

}
