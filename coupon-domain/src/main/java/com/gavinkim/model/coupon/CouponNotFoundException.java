package com.gavinkim.model.coupon;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException() {
        super();
    }

    public CouponNotFoundException(String message) {
        super(message);
    }

    public CouponNotFoundException(Throwable cause) {
        super(cause);
    }
}
