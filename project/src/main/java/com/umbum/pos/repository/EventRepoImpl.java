package com.umbum.pos.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.umbum.pos.model.Event;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EventRepoImpl implements EventRepo {
    private final JdbcTemplate jdbcTemplate;

    public EventRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Event> readAll(long productId) {
        String query = "SELECT DISCOUNT_RATE, EVENT_ID, EVENT_NAME, START_DATE, FINISH_DATE AS END_DATE FROM EVENT WHERE PRODUCT_ID = ?";
        return jdbcTemplate.query(query, new Object[]{productId}, new BeanPropertyRowMapper<>(
            Event.class));
    }
}
