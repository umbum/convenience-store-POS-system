package com.umbum.pos.model;

import lombok.Data;

@Data
public class Product {
    private long code;
    private String name;
    private int unitPrice;
    private int discount;
    // TODO 이벤트를 묶어서 가져올거면, 이벤트를 묶어서 가져오는 데이터 타입을 하나 더 만들어야겠네
}
