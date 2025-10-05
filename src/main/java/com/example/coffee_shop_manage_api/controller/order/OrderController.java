package com.example.coffee_shop_manage_api.controller.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.model.Order;
import com.example.coffee_shop_manage_api.service.OrderService;

@RestController
@RequestMapping("/api/orders/v1")
public class OrderController extends AbstractCommonController<Order, String> {

 public OrderController(OrderService orderService) {
  super(orderService);
 }
}
