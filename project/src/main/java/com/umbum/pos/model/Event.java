package com.umbum.pos.model;

import lombok.Data;

@Data
public class Event {
    private long eventId;    // PK
    private long productId;  // FK
    private int discountRate;
    private String eventName;
    private String startDate;
    private String endDate;

}
