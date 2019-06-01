package com.umbum.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

// TODO : Kotlin으로 바꿔보자~~~~!!

@Data
public class Payment {
    @JsonIgnore
    private long salesNum;
    private String method;
    private long amount;
}
