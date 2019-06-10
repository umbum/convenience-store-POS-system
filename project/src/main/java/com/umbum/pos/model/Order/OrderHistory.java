package com.umbum.pos.model.Order;

import java.util.List;

import lombok.Data;

/**
 * 처음에 테이블을 설계할 때, 설계 미스로 다음과 같은 상황이 되었다.
 * 발주(Order) 1개가 입고(Receive) 1개에 대응되는게 아니라,
 * 발주를 넣으면 그게 각각의 업체로 들어가서 업체 마다 배송(Send)을 하게 되고, 따라서 입고 되는건 발주와 연관된 업체의 수 만큼이다.
 */
@Data
public class OrderHistory {
    private Order order;
    private List<CompanyOrder> companyOrderList;
}
