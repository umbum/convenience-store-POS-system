package com.umbum.pos.model.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderProduct {
    @JsonIgnore
    private long orderId;

    private long productId;
    private String productName;
    private int  orderQuantity;

    private int receiveQuantity;

    private int orderPrice;
}
