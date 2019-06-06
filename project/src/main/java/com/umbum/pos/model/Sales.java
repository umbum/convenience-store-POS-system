package com.umbum.pos.model;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Sales {
    @JsonIgnore
    private long salesId;    // PK

    @Nullable
    private Long customerId;

    @JsonIgnore
    private long branchId;

    @JsonIgnore
    private String salesTime;

    @JsonIgnore
    private long receiptId;

    @JsonIgnore
    private int cancelCheck;

    private int amount;
}
