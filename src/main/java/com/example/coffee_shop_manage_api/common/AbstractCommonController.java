package com.example.coffee_shop_manage_api.common;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.coffee_shop_manage_api.global.ApiResponseData;

public abstract class AbstractCommonController<T, ID> {
  protected final AbstractCommonService<T, ID> service;

  protected AbstractCommonController(AbstractCommonService<T, ID> service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<ApiResponseData<List<T>>> getAll() {
    List<T> data = service.findAll();
    return ResponseEntity.ok(ApiResponseData.success("Fetched all successfully", data));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponseData<T>> getById(@PathVariable ID id) {
    Optional<T> entity = service.findById(id);
    return entity
        .map(value -> ResponseEntity.ok(ApiResponseData.success("Fetched successfully", value)))
        .orElse(ResponseEntity.status(404).body(ApiResponseData.<T>notFound("Entity not found")));
  }

  @PostMapping
  public ResponseEntity<ApiResponseData<T>> create(@RequestBody T entity) {
    T created = service.create(entity);
    return ResponseEntity.status(201).body(ApiResponseData.success("Created successfully", created, 201));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseData<Void>> delete(
      @PathVariable ID id) {
    try {
      service.delete(id);
      return ResponseEntity.ok(ApiResponseData.<Void>success("Deleted successfully", null));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(404).body(ApiResponseData.<Void>notFound(e.getMessage()));
    }
  }
}
