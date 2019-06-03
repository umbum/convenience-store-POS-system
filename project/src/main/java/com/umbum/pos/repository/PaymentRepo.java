package com.umbum.pos.repository;


import java.util.List;

import com.umbum.pos.model.Payment;

public interface PaymentRepo {
    public Payment read();
    public long create(Payment payment);
    public long createAll(List<Payment> paymentList);
}
