package com.example.coffee_shop_manage_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.Menu;
import com.example.coffee_shop_manage_api.model.Order;
import com.example.coffee_shop_manage_api.model.OrderItem;
import com.example.coffee_shop_manage_api.repository.MenuRepository;
import com.example.coffee_shop_manage_api.repository.OrderItemRepository;
import com.example.coffee_shop_manage_api.repository.OrderRepository;

@Service
public class OrderItemService extends AbstractCommonService<OrderItem, String> {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository,
            MenuRepository menuRepository) {
        super(orderItemRepository);
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
    }

    public List<OrderItem> insertMany(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Order order = orderRepository.findById(item.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found: " + item.getOrder().getId()));

            Menu menu = menuRepository.findById(item.getMenu().getId())
                    .orElseThrow(() -> new RuntimeException("Menu not found: " + item.getMenu().getId()));
            item.setSubtotal(item.getSubtotal());
            item.setQuantity(item.getQuantity());
            item.setOrder(order);
            item.setMenu(menu);
        }

        return repository.saveAll(orderItems);
    }
}
