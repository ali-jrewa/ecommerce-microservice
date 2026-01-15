package com.allosh.ecommerce.service;

import com.allosh.ecommerce.mapper.OrderLineMapper;
import com.allosh.ecommerce.orderline.OrderLineRequest;
import com.allosh.ecommerce.orderline.OrderLineResponse;
import com.allosh.ecommerce.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest request){
        var orderLine = orderLineMapper.toOrderLine(request);
        return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId){
        return orderLineRepository.findAllByOrderId(orderId).stream().map(orderLineMapper::toOrderLineResponse).collect(Collectors.toList());
    }
}
