package com.allosh.ecommerce.payment;

import com.allosh.ecommerce.customer.CustomerResponse;
import com.allosh.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer

) {
}
