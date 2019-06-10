package com.umbum.pos.model.Order;

import java.util.List;

import lombok.Data;

/**
 * DB의 SEND 테이블 + PRODUCT에 해당한다고 보면 된다.
 */
@Data
public class CompanyOrder {
    private long companyId;
    private String companyName;
    private String sendDate;
    private int receiveCheck;    // true이면 receiveDate가 set된다.
    private String receiveDate;

    private List<OrderProduct> orderProductList;
}
