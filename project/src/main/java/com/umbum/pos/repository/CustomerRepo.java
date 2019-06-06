package com.umbum.pos.repository;

import com.umbum.pos.model.Customer;

public interface CustomerRepo {
    public Customer read(String phone);
    public int create(Customer customer);
    public int update(long customerId, long mileage);
}
