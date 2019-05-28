package com.umbum.pos.model;

import lombok.Data;

@Data
public class Product {
    private long code;
    private String name;
    private int unitPrice;
    private int discount;
    // 여기서 event도.
}
