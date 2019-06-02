package com.umbum.pos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SalesInfo {
    private Sales sales;
    private List<Payment> payments;
    @JsonProperty("sales_products")
    private List<SalesProduct> salesProducts;
}
