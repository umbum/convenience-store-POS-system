package com.umbum.pos.model.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Order {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long orderId;
    @JsonIgnore
    private long branchId;
    private String orderDate;
    private int amount;
}
