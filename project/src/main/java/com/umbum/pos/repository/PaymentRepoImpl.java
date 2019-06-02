package com.umbum.pos.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.umbum.pos.model.Payment;

@Repository
public class PaymentRepoImpl implements PaymentRepo{
    @Override
    public Payment read() {
        return null;
    }

    @Override
    public long create(Payment payment) {
        return 0;
    }

    @Override
    public long createAll(List<Payment> payment) {
        return 0;
    }
}
