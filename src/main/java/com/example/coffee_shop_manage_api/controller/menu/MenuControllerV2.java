package com.example.coffee_shop_manage_api.controller.menu;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.global.ApiResponseData;
import com.example.coffee_shop_manage_api.model.Menu;
import com.example.coffee_shop_manage_api.service.MenuService;

@RestController
@RequestMapping("/api/v2/menus")
@PreAuthorize("hasRole('ADMIN')")
public class MenuControllerV2 {
 private final MenuService menuService;

 public MenuControllerV2(MenuService menuService) {
  this.menuService = menuService;
 }

 @PostMapping("/batch")
 public ResponseEntity<ApiResponseData<Integer>> createAll(@RequestBody List<Menu> request) {
  List<Menu> createdMenus = menuService.createAll(request);
  if (!createdMenus.isEmpty()) {
   return ResponseEntity.status(201).body(ApiResponseData.success("Created successfully", createdMenus.size(), 201));
  } else {
   return ResponseEntity.status(400).body(ApiResponseData.error("Created failed", 400));
  }
 }
}
