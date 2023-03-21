package com.phonestore.constant;

public enum PaymentMethodEnum {

    PAYPAL("PAYPAL"), NORMAL("PHỔ THÔNG");

    String method;

    PaymentMethodEnum(String method) {
        this.method = method;
    }

    public String method() {
        return this.method;
    }
}
