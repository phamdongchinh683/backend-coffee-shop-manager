package com.example.coffee_shop_manage_api.controller.orderItem;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.global.ApiResponseData;
import com.example.coffee_shop_manage_api.model.OrderItem;
import com.example.coffee_shop_manage_api.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items/v1")
@PreAuthorize("hasRole('GUEST')")
public class OrderItemController extends AbstractCommonController<OrderItem, String> {

 private final OrderItemService orderItemService;

 public OrderItemController(OrderItemService orderItemService) {
  super(orderItemService);
  this.orderItemService = orderItemService;
 }

 @PostMapping("/batch")
 public ResponseEntity<ApiResponseData<List<OrderItem>>> insertMany(@RequestBody List<OrderItem> orderItems) {
  List<OrderItem> createdOrderItems = orderItemService.createAll(orderItems);
  return ResponseEntity.ok(ApiResponseData.success("Created successfully", createdOrderItems));
 }
}
