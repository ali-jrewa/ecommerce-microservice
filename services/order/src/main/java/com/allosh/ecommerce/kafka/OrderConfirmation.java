package com.allosh.ecommerce.kafka;

import com.allosh.ecommerce.customer.CustomerResponse;
import com.allosh.ecommerce.order.PaymentMethod;
import com.allosh.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String references,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
