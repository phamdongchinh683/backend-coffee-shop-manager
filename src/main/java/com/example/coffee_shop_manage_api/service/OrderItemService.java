package com.example.coffee_shop_manage_api.service;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.OrderItem;
import com.example.coffee_shop_manage_api.repository.OrderItemRepository;

@Service
public class OrderItemService extends AbstractCommonService<OrderItem, String> {
    
    public OrderItemService(OrderItemRepository orderItemRepository) {
        super(orderItemRepository);
    }
}
