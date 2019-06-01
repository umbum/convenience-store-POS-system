package com.umbum.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SalesProduct {
    private long prodCode;
    @JsonIgnore
    private long salesNum;
    private int quantity;
}
