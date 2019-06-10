package com.umbum.pos.model.Order;

import lombok.Data;

@Data
public class OrderProduct {
    private long orderId;

    private long productId;
    private String productName;
    private int  orderQuantity;

    private int receiveQuantity;

    private int orderPrice;
}
