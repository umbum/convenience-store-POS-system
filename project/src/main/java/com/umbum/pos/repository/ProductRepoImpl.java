package com.umbum.pos.repository;

import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ProductRepoImpl implements ProductRepo {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Product read(long productId) {
        String query = "SELECT PRODUCT_ID, BARCODE, PRICE, NAME, CSPS, FRS, ORD_PRI AS ORDER_PRICE FROM PRODUCT WHERE PRODUCT_ID = ?";

        Product product = null;
        try {
            product = jdbcTemplate.queryForObject(query, new Object[]{productId}, new BeanPropertyRowMapper<>(Product.class));
        } catch (EmptyResultDataAccessException e) {
            log.debug("Incorrect result size: expected 1, actual 0");
        }
        return product;
    }

}
