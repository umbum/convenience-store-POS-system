package com.umbum.pos.model;

import lombok.Data;

/**
 * 이렇게 되면 전혀 RESTful 하지 않음. 클라이언트의 datatable에서 표현해야 하는 column대로 Model을 만들면, 클라이언트가 어떤 어떤 데이터를 출력하는가에 엄청나게 의존하게됨.
 * Order에서 receive에 대한 정보도 한꺼번에 가져오기 때문에 굳이 ProductInstockHistory와 그에 대한 Repository가 있을 필요가 없다.
 * 나중에 리팩토링할 것.
 */
@Data
public class ProductInstockHistory {

    private long orderId;
    private String orderDate;

    private long productId;
    private String productName;
    private int orderQuantity;

    private long companyId;
    private String companyName;

    private String receiveDate;
    private int receiveQuantity;

    private int orderPrice;
}
