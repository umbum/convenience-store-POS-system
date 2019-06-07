package com.umbum.pos.model;

import lombok.Data;

@Data
public class OrderProduct {
    private long orderId;
    private String orderDate;

    private long productId;
    private String productName;
    private int  orderQuantity;

    private long companyId;
    private String companyName;
}
