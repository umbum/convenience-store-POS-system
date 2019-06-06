package com.umbum.pos.model;

import lombok.Data;


@Data
public class Customer {
    private long customerId;
    private String cusName;
    private String sex;
    private String birth;
    private String phone;
    private long mileage;
}
