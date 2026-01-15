package com.allosh.ecommerce.mapper;

import com.allosh.ecommerce.order.Order;
import com.allosh.ecommerce.orderline.OrderLine;
import com.allosh.ecommerce.orderline.OrderLineRequest;
import com.allosh.ecommerce.orderline.OrderLineResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request){
        return OrderLine
                .builder()
                    .id(request.id())
                    .order(Order.builder().id(request.orderId()).build())
                    .productId(request.productId())
                    .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine){
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
