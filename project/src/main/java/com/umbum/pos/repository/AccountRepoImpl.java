package com.umbum.pos.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.umbum.pos.model.Account;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AccountRepoImpl implements AccountRepo {
    private final JdbcTemplate jdbcTemplate;

    public AccountRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account read(String username) {
        // authorities 때문에 objectMapper는 못쓰고 직접 Account를 만들어야 한다.
        String accountQuery   = "SELECT USERNAME, PASSWORD, BRANCH_ID FROM ACCOUNT WHERE USERNAME = ?";
        String authorityQuery = "SELECT AUTHORITY FROM AUTHORITY WHERE USERNAME = ?";

        Map<String, Object> accountRecord = jdbcTemplate.queryForMap(accountQuery, username);
        List<String> authorityList = jdbcTemplate.queryForList(authorityQuery, new Object[]{username}, String.class);

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String authority : authorityList) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }

        return new Account(
            (String)accountRecord.get("username"),
            (String)accountRecord.get("password"),
            authorities);
    }

    @Transactional
    @Override
    public int create(Account account) {
        String accountQuery   = "INSERT INTO ACCOUNT VALUES(?, ?, ?)";
        String authorityQuery = "INSERT INTO AUTHORITY VALUES(?, ?)";

        int result = jdbcTemplate.update(accountQuery,
                                        account.getUsername(),
                                        account.getPassword(),
                                        account.getBranchId());

        for (GrantedAuthority authority : account.getAuthorities()) {
            result += jdbcTemplate.update(authorityQuery, account.getUsername(), authority.toString());
        }

        return 0;
    }
}
