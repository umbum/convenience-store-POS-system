package com.umbum.pos.repository;

import com.umbum.pos.model.Account;

public interface AccountRepo {
    public Account read(String username);
    public int create(Account account);
}
