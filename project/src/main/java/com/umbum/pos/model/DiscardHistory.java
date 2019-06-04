package com.umbum.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DiscardHistory {
    private long productId;
    private String productName;
    private int quantity;
    private String date;
}
