package com.example.coffee_shop_manage_api.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.dto.response.MenuResponse;
import com.example.coffee_shop_manage_api.dto.response.OrderItemResponse;
import com.example.coffee_shop_manage_api.dto.response.OrderResponse;
import com.example.coffee_shop_manage_api.global.OrderStatus;
import com.example.coffee_shop_manage_api.kafka.producer.ReportProducer;
import com.example.coffee_shop_manage_api.model.CoffeeTable;
import com.example.coffee_shop_manage_api.model.Order;
import com.example.coffee_shop_manage_api.model.User;
import com.example.coffee_shop_manage_api.repository.OrderRepository;

@Service
public class OrderService extends AbstractCommonService<Order, String> {

    private final OrderRepository orderRepository;

    private final CoffeeTableService coffeeTableService;
    private final UserService userService;
    private final ReportProducer reportProducer;

    public OrderService(OrderRepository orderRepository, CoffeeTableService coffeeTableService,
            UserService userService, ReportProducer reportProducer) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.coffeeTableService = coffeeTableService;
        this.userService = userService;
        this.reportProducer = reportProducer;
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

    public Page<OrderResponse> findOrdersByUserId(String userId, int page, int size) {
        Page<Order> orderPage = orderRepository.findByUserId(userId, PageRequest.of(page - 1, size));

        return orderPage.map(order -> {
            Order orderWithItems = orderRepository.findOrderWithItems(order.getId());

            return new OrderResponse(
                    orderWithItems.getId(),
                    orderWithItems.getTotalAmount(),
                    orderWithItems.getStatus().toString(),
                    orderWithItems.getOrderItems() != null ? orderWithItems.getOrderItems().stream()
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
        });
    }

    @Override
    public Order create(Order entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Order payload is required");
        }
        if (entity.getTable() == null || entity.getTable().getId() == null) {
            throw new IllegalArgumentException("Table id is required");
        }
        if (entity.getUser() == null || entity.getUser().getId() == null) {
            throw new IllegalArgumentException("User id is required");
        }
        if (entity.getTotalAmount() == null) {
            throw new IllegalArgumentException("Total amount is required");
        }

        CoffeeTable table = coffeeTableService.findById(entity.getTable().getId())
                .orElseThrow(() -> new RuntimeException("Table not found"));
        User user = userService.findById(entity.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setTable(table);
        order.setUser(user);
        order.setTotalAmount(entity.getTotalAmount());
        order.setStatus(OrderStatus.OPEN);

        return orderRepository.save(order);
    }

    public void updateOrderStatusById(String id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.updateOrderStatusById(id, status);

        if (status == OrderStatus.PAID) {
            reportProducer.sendTotalAmount(order.getTotalAmount());
        }
    }
}