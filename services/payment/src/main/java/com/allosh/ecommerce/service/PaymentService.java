package com.allosh.ecommerce.service;

import com.allosh.ecommerce.mapper.PaymentMapper;
import com.allosh.ecommerce.notification.NotificationProducer;
import com.allosh.ecommerce.payment.PaymentNotificationRequest;
import com.allosh.ecommerce.payment.PaymentRequest;
import com.allosh.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest paymentRequest){
        var payment = paymentRepository.save(paymentMapper. toPayment(paymentRequest));

        notificationProducer.sendNotification(new PaymentNotificationRequest(
                paymentRequest.orderReference(),
                paymentRequest.amount(),
                paymentRequest.paymentMethod(),
                paymentRequest.customer().firstName(),
                paymentRequest.customer().lastName(),
                paymentRequest.customer().email()
        ));
        return payment.getId();
    }
}
