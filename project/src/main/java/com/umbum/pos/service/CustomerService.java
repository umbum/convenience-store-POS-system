package com.umbum.pos.service;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.Customer;
import com.umbum.pos.repository.CustomerRepo;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer getCustomer(String phone) {
        return customerRepo.read(phone);
    }
}
