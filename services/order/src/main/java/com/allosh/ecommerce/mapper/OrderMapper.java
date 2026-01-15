package com.allosh.ecommerce.mapper;

import com.allosh.ecommerce.order.Order;
import com.allosh.ecommerce.order.OrderRequest;
import com.allosh.ecommerce.order.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request){
        return Order
                .builder()
                       .id(request.id())
                       .customerId(request.customerId())
                       .references(request.references())
                       .totalAmount(request.amount())
                       .paymentMethod(request.paymentMethod())

                .build();
    }

    public OrderResponse fromOrder(Order order){
        return new OrderResponse(
                order.getId(),
                order.getReferences(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
            );
    }
}
