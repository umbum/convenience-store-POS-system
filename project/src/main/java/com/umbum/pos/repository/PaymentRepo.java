package com.umbum.pos.repository;


import java.util.List;

import com.umbum.pos.model.Payment;

public interface PaymentRepo {
    public List<Payment> readAll(long salesId);
    public int create(Payment payment);
    public int[] createAll(List<Payment> paymentList);
}
