package com.umbum.pos.model;

import lombok.Data;

@Data
public class ProductInstockHistory {
    /**
     * 물품이 입고될 때, 발주 내역서가 물품과 함께 들어오며 연관된 ORDER_ID가 내역서에 적혀있다.
     * 그래서 입고 시점에서 물건을 추가할 때 내역서에 있는 ORDER_ID를 보고 바코드를 찍으면 입력되는 식이므로
     * 프론트엔드에서 ORDER_ID를 입력하는 입력 박스를 제공할 것.
     */
    private long productId;
    private long companyId;
    private long orderId;

    private String receiveDate;
    private String orderDate;

    private String productName;
    private String companyName;

    private int orderQuantity;
    private int receiveQuantity;

    private int orderPrice;
}
