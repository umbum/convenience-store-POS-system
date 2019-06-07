package com.umbum.pos.model;

import lombok.Data;

@Data
public class Product {
    private long productId;
    private long companyId;
    private int price;
    private String name;
    private String barcode;
//    private String CSPS;    // 편의점 전용상품?
//    private String FRS;     // 뭐라그랬더라?
    private int orderPrice;
}
