package com.umbum.pos.repository;

import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.umbum.pos.model.Sales;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class SalesRepoImpl implements SalesRepo {

    private final JdbcTemplate jdbcTemplate;

    public SalesRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sales read(long salesId) {
        String query = "SELECT * FROM SALES WHERE SALES_ID = ?";
        return null;
    }

    @Override
    public List<Sales> readAll(String date) {
        String query = "SELECT SALES_ID, CUSTOMER_ID, BRANCH_ID, SALES_TIME, RECEIPT_ID, CANCEL_CHECK, AMOUNT, EARNED_MILEAGE\n"
            + "FROM SALES\n"
            + "WHERE SALES_TIME BETWEEN TRUNC(?) AND TRUNC(?)";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date dateDate = dateFormat.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateDate);
            cal.add(Calendar.DATE, 1);
            Date nextDate = cal.getTime();
            return jdbcTemplate.query(query, new Object[]{dateDate, nextDate}, new BeanPropertyRowMapper<>(Sales.class));
        } catch (ParseException e) {
            // 잘못된 날짜 형식이 들어왔을 때.
            throw new RuntimeException(e);
        }
    }

    @Override
    public Sales readByReceiptId(long receiptId) {
        String query = "SELECT SALES_ID, CUSTOMER_ID, BRANCH_ID, SALES_TIME, RECEIPT_ID, CANCEL_CHECK, AMOUNT, EARNED_MILEAGE\n"
            + "FROM SALES\n"
            + "WHERE RECEIPT_ID = ?;\n";
        Sales sales = null;
        try {
            sales = jdbcTemplate.queryForObject(query, new Object[]{receiptId}, new BeanPropertyRowMapper<>(Sales.class));
        } catch (EmptyResultDataAccessException e) {
            log.debug("Incorrect result size: expected 1, actual 0");
        }

        return sales;
    }

    /**
     * salesId를 리턴하니 주의할 것.
     * INSERT 하면서 동시에 key(salesId)를 반환받기 위해 KeyHolder 를 사용하려..고 했으나 오라클은 지원을 안한다고 하네
     * https://stackoverflow.com/questions/54239345/spring-data-jdbc-dataretrievalfailureexception-unable-to-cast-oracle-sql-row
     * 그래서, SELECT로 SEQUENCE를 반환받으면서 시퀀스를 증가시킨 다음, 그 키로 INSERT하는 쿼리 두개로 분할한다.
     * 반환받는 시점에 동시에 시퀀스가 증가하기 때문에 기본키 중복이 일어나지는 않는다.
     *
     * @param sales
     * @return salesId : long
     */
    @Transactional
    @Override
    public long create(Sales sales) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        sales.setSalesTime(dateFormat.format(Calendar.getInstance().getTime()));
        sales.setCancelCheck(0);

        sales.setSalesId(
            jdbcTemplate.queryForObject("SELECT sales_id_seq.NEXTVAL FROM DUAL", Long.class)
        );
        sales.setReceiptId(sales.getSalesId());

        String query = "INSERT INTO SALES(sales_id, customer_id, branch_id, sales_time, receipt_id, cancel_check, amount, earned_mileage) "
            + "VALUES(?, ?, ?, TO_DATE(?,'YYYYMMDD HH24:MI:SS'), ?, ?, ?, ?)";
        jdbcTemplate.update(query,
            sales.getSalesId(),
            sales.getCustomerId(),
            sales.getBranchId(),
            sales.getSalesTime(),
            sales.getReceiptId(),
            sales.getCancelCheck(),
            sales.getAmount(),
            sales.getEarnedMileage()
            );

        return sales.getSalesId();
    }

    @Override
    public int updateCancelCheck(long salesId) {
        String query = "UPDATE SALES\n"
            + "SET CANCEL_CHECK = 1\n"
            + "WHERE SALES_ID = ?";

        return jdbcTemplate.update(query, salesId);
    }
}
