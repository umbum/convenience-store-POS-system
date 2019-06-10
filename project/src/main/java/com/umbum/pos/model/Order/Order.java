package com.umbum.pos.model.Order;

import java.util.List;

import lombok.Data;

@Data
public class Order {
    private long branchId;
    private String orderDate;
    private int amount;
}
