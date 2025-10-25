package com.example.coffee_shop_manage_api.controller.order;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.dto.response.OrderResponse;
import com.example.coffee_shop_manage_api.global.ApiResponseData;
import com.example.coffee_shop_manage_api.global.OrderStatus;
import com.example.coffee_shop_manage_api.global.ResponsePagination;
import com.example.coffee_shop_manage_api.model.Order;
import com.example.coffee_shop_manage_api.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController extends AbstractCommonController<Order, String> {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    super(orderService);
    this.orderService = orderService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponseData<OrderResponse>> findOrderWithItems(@PathVariable String id) {
    OrderResponse orderResponse = orderService.findOrderWithItems(id);
    return ResponseEntity.ok(ApiResponseData.success("Order found", orderResponse));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponseData<Void>> updateOrderStatusById(@PathVariable String id,
      @RequestBody OrderStatus status) {
    orderService.updateOrderStatusById(id, status);
    return ResponseEntity.ok(ApiResponseData.success("Order updated", null));
  }

  @GetMapping("/user/{userId}")
  @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
  public ResponseEntity<ApiResponseData<ResponsePagination<OrderResponse>>> findOrdersByUserId(
      @PathVariable String userId,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
    Page<OrderResponse> orderPage = orderService.findOrdersByUserId(userId, page, size);
    ResponsePagination<OrderResponse> pagination = new ResponsePagination<OrderResponse>(
        orderPage.getContent(),
        page,
        orderPage.getSize(),
        orderPage.getTotalElements(),
        orderPage.getTotalPages());
    return ResponseEntity.ok(ApiResponseData.success("Orders found", pagination));
  }
}
