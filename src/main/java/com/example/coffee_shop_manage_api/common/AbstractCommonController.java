package com.example.coffee_shop_manage_api.common;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.coffee_shop_manage_api.global.ApiResponse;

public abstract class AbstractCommonController<T, ID> {
 protected final AbstractCommonService<T, ID> service;

 protected AbstractCommonController(AbstractCommonService<T, ID> service) {
  this.service = service;
 }

 @GetMapping
 public ResponseEntity<ApiResponse<List<T>>> getAll() {
  List<T> data = service.findAll();
  return ResponseEntity.ok(ApiResponse.success("Fetched all successfully", data));
 }

 @GetMapping("/{id}")
 public ResponseEntity<ApiResponse<T>> getById(@PathVariable ID id) {
  Optional<T> entity = service.findById(id);
  return entity
    .map(value -> ResponseEntity.ok(ApiResponse.success("Fetched successfully", value)))
    .orElse(ResponseEntity.status(404).body(ApiResponse.notFound("Entity not found")));
 }

 @PostMapping
 public ResponseEntity<ApiResponse<T>> create(@RequestBody T entity) {
  T created = service.create(entity);
  return ResponseEntity.status(201).body(ApiResponse.success("Created successfully", created, 201));
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<ApiResponse<Void>> delete(@PathVariable ID id) {
  try {
   service.delete(id);
   return ResponseEntity.ok(ApiResponse.success("Deleted successfully", null));
  } catch (IllegalArgumentException e) {
   return ResponseEntity.status(404).body(ApiResponse.notFound(e.getMessage()));
  }
 }
}
