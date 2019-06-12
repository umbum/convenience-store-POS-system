package com.umbum.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisposalProduct {
    private long productId;
    private long disposalId;
    private int quantity;
    private String disDate;
    private String productName;
}
