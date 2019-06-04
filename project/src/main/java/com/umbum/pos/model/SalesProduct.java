package com.umbum.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SalesProduct {
    private long productId;
    @JsonIgnore
    private long salesId;
    private int quantity;
}
