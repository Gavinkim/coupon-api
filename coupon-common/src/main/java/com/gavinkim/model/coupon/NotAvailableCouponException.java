package com.gavinkim.model.coupon;

public class NotAvailableCouponException extends RuntimeException {

    public NotAvailableCouponException() {
        super();
    }

    public NotAvailableCouponException(String message) {
        super(message);
    }

    public NotAvailableCouponException(Throwable cause) {
        super(cause);
    }
}
