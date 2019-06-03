package com.umbum.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Sales {
    @JsonIgnore
    private long salesNum;    // PK
    private long cusNum;
    private long braNum;
    @JsonIgnore
    private String salesTime; // TODO Date 타입???
    @JsonIgnore
    private long recNum;
    @JsonIgnore
    private boolean cancelCheck;    // whetherCanceled 정도가 더 낫지 않나...
    private int amount;    // NUMBER(int자리수 -1)
}
