package com.example.coffee_shop_manage_api.controller.table;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.model.CoffeeTable;
import com.example.coffee_shop_manage_api.service.CoffeeTableService;

@RestController
@RequestMapping("/api/tables/v1")
@PreAuthorize("hasRole('ADMIN')")
public class CoffeeTableController extends AbstractCommonController<CoffeeTable, String> {

 public CoffeeTableController(CoffeeTableService coffeeTableService) {
  super(coffeeTableService);
 }
}
