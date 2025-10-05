package com.example.coffee_shop_manage_api.controller.orderItem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.model.OrderItem;
import com.example.coffee_shop_manage_api.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items/v1")
public class OrderItemController extends AbstractCommonController<OrderItem, String> {

 public OrderItemController(OrderItemService orderItemService) {
  super(orderItemService);
 }
}
