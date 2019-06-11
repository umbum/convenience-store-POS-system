package com.umbum.pos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;

@Data
public class Payment {
    @JsonIgnore
    private long salesId;

    private PaymentCode paymentCode;

    private long amount;

    @Getter
    public enum PaymentCode {
        CASH(1, "현금"),
        CARD(2, "카드");

        private final int code;
        private final String name;

        PaymentCode(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static PaymentCode fromCode(int _code){
            for(PaymentCode code : PaymentCode.values()){
                if (code.getCode() == _code) {
                    return code;
                }
            }
            throw new IllegalArgumentException();
        }

        public static PaymentCode fromName(String text){
            for(PaymentCode code : PaymentCode.values()){
                if (code.getName().equals(text)) {
                    return code;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
