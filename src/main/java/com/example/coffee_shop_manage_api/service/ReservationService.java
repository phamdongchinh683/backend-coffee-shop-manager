package com.example.coffee_shop_manage_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.global.OrderStatus;
import com.example.coffee_shop_manage_api.model.CoffeeTable;
import com.example.coffee_shop_manage_api.model.Order;
import com.example.coffee_shop_manage_api.model.OrderItem;
import com.example.coffee_shop_manage_api.model.Reservation;
import com.example.coffee_shop_manage_api.model.User;
import com.example.coffee_shop_manage_api.repository.ReservationRepository;

@Service
public class ReservationService extends AbstractCommonService<Reservation, String> {

    private final CoffeeTableService coffeeTableService;

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    private final UserService userService;

    public ReservationService(
            ReservationRepository reservationRepository,
            CoffeeTableService coffeeTableService,
            OrderService orderService,
            OrderItemService orderItemService,
            UserService userService) {
        super(reservationRepository);
        this.coffeeTableService = coffeeTableService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.userService = userService;
    }

    @Transactional
    public String createReservation(String tableId, String userId, List<OrderItem> items,
            BigDecimal totalAmount) {
        CoffeeTable table = coffeeTableService.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (items == null || items.isEmpty()) {
            throw new RuntimeException("Order items cannot be empty");
        }

        Order order = new Order();
        order.setTable(table);
        order.setUser(user);
        order.setStatus(OrderStatus.OPEN);
        order.setTotalAmount(totalAmount);
        order = orderService.create(order);

        for (OrderItem item : items) {
            item.setOrder(order);
            item.setMenu(item.getMenu());
            item.setQuantity(item.getQuantity());
            item.setSubtotal(item.getSubtotal());
            item.setOrderNote(item.getOrderNote());
        }

        orderItemService.createAll(items);

        return "oke";
    }

}
