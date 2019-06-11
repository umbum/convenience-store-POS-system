package com.umbum.pos.model;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sales {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long salesId;    // PK

    @Nullable
    private Long customerId;
    @Nullable
    private Integer earnedMileage;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long branchId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String salesTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long receiptId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int cancelCheck;

    private int amount;

}
