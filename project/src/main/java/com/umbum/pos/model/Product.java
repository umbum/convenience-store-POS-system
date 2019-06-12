package com.umbum.pos.model;

import lombok.Data;

@Data
public class Product {
    private long productId;
    private long companyId;
    private int price;
    private String name;
    private String barcode;
//    private String CSPS;    // 편의점 전용상품 여부
//    private String FRS;     // 파손 위험 상품 여부
    private int orderPrice;

    private String companyName;
}
