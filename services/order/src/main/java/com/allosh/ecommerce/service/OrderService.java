package com.allosh.ecommerce.service;

import com.allosh.ecommerce.customer.CustomerClient;
import com.allosh.ecommerce.exception.BusinessException;
import com.allosh.ecommerce.kafka.OrderConfirmation;
import com.allosh.ecommerce.kafka.OrderProducer;
import com.allosh.ecommerce.mapper.OrderMapper;
import com.allosh.ecommerce.order.OrderRequest;
import com.allosh.ecommerce.order.OrderResponse;
import com.allosh.ecommerce.orderline.OrderLineRequest;
import com.allosh.ecommerce.product.ProductClient;
import com.allosh.ecommerce.product.PurchaseRequest;
import com.allosh.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request){
        //check the customer --> openFeign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided ID" +request.customerId()));

        //purchase the product --> product-ms(RestTemplate)
        var purchasedProducts =  productClient.purchaseProducts(request.products());

        //persist the order
        var order = orderRepository.save(orderMapper.toOrder(request));

        //persist the order line
        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()

                    )
            );
        }

        //start payment process

        //send the order confirmation --> notification-ms (kafka)]
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.references(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts

        ));

    return order.getId();
    }

    public List<OrderResponse> findAll(){
        return orderRepository.findAll().stream().map(orderMapper::fromOrder).collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId){
        return orderRepository.findById(orderId).map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d ",orderId)));
    }

}
