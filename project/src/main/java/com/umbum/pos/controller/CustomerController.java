package com.umbum.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.umbum.pos.model.Customer;
import com.umbum.pos.service.CustomerService;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseBody
    @GetMapping("/customer/{phone}")
    public Customer getCustomer(@PathVariable String phone) {
        return customerService.getCustomer(phone);
    }
}
