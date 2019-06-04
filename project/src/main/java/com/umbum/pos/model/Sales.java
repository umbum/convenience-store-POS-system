package com.umbum.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Sales {
    @JsonIgnore
    private long salesId;    // PK
    private long customerId;
    private long branchId;
    @JsonIgnore
    private String salesTime;
    @JsonIgnore
    private long receiptId;
    @JsonIgnore
    private boolean cancelCheck;
    private int amount;
}
