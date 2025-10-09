package com.example.coffee_shop_manage_api.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.dto.response.MenuResponse;
import com.example.coffee_shop_manage_api.dto.response.OrderItemResponse;
import com.example.coffee_shop_manage_api.dto.response.OrderResponse;
import com.example.coffee_shop_manage_api.model.Order;
import com.example.coffee_shop_manage_api.repository.OrderRepository;

@Service
public class OrderService extends AbstractCommonService<Order, String> {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }

    public OrderResponse findOrderWithItems(String id) {
        Order order = orderRepository.findOrderWithItems(id);
        if (order == null) {
            return null;
        }

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus().toString(),
                order.getOrderItems() != null ? order.getOrderItems().stream()
                        .map(orderItem -> new OrderItemResponse(
                                orderItem.getId(),
                                orderItem.getQuantity(),
                                orderItem.getOrderNote(),
                                orderItem.getSubtotal(),
                                new MenuResponse(
                                        orderItem.getMenu().getId(),
                                        orderItem.getMenu().getMenuName(),
                                        orderItem.getMenu().getCosts(),
                                        orderItem.getMenu().getSizes())))
                        .collect(Collectors.toList()) : null);
    }
}
