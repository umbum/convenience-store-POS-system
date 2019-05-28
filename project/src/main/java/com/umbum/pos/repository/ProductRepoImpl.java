package com.umbum.pos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public Product getProduct(long productCode) {
        String query = "SELECT * FROM PRODUCT WHERE CODE = ?";

        // TODO : 이러면 null을 리턴해야하잖아..
        // 여기서 catch해서 null을 리턴하고 상위에서 null check를 수행하는게 낫나?
        // 아니면 상위로 throw해서 위에서 잡아서 처리하는게 낫나?
        Product product = null;
        try {
            product = jdbcTemplate.queryForObject(query, new Object[]{productCode}, new BeanPropertyRowMapper<>(Product.class));
        } catch (EmptyResultDataAccessException e) {
            log.debug("Incorrect result size: expected 1, actual 0");
        }

        return product;
    }

}
