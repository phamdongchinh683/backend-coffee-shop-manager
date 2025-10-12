package com.example.coffee_shop_manage_api.controller.table;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.global.ApiResponseData;
import com.example.coffee_shop_manage_api.model.CoffeeTable;
import com.example.coffee_shop_manage_api.service.CoffeeTableService;

@RestController
@RequestMapping("/api/tables/v1")
@PreAuthorize("hasRole('ADMIN')")
public class CoffeeTableController extends AbstractCommonController<CoffeeTable, String> {

 private final CoffeeTableService coffeeTableService;

 public CoffeeTableController(CoffeeTableService coffeeTableService) {
  super(coffeeTableService);
  this.coffeeTableService = coffeeTableService;

 }

 @PutMapping("/{id}")
 public ResponseEntity<ApiResponseData> update(@PathVariable String id, @RequestBody CoffeeTable request) {
  CoffeeTable updatedTable = coffeeTableService.updateStatusByTableId(id, request);

  return ResponseEntity.status(200).body(ApiResponseData.success("Updated successfully", updatedTable));
 }

}
