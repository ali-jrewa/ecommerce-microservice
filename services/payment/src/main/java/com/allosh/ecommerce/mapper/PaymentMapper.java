package com.allosh.ecommerce.mapper;

import com.allosh.ecommerce.payment.Payment;
import com.allosh.ecommerce.payment.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request){
        return Payment.builder()
                .id(request.id())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .orderId(request.orderId())
                .id(request.id())
                .build();
    }
}
