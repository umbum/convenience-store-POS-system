package com.umbum.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SalesProduct {
    private long productId;
    @JsonIgnore
    private long salesId;

    private int quantity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String productName;

    private int salesPrice;
}
