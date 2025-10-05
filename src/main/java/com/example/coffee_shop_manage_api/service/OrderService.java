package com.example.coffee_shop_manage_api.service;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.Order;
import com.example.coffee_shop_manage_api.repository.OrderRepository;

@Service
public class OrderService extends AbstractCommonService<Order, String> {
    
    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
    }
}
