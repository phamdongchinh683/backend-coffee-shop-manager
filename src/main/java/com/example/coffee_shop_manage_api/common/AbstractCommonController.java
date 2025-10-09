package com.example.coffee_shop_manage_api.common;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.coffee_shop_manage_api.global.ApiResponseData;
import com.example.coffee_shop_manage_api.global.ResponsePagination;

public abstract class AbstractCommonController<T, ID> {
  protected final AbstractCommonService<T, ID> service;

  protected AbstractCommonController(AbstractCommonService<T, ID> service) {
    this.service = service;
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

  @GetMapping(params = { "page", "size" })
  public ResponseEntity<ApiResponseData<ResponsePagination<T>>> getPage(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<T> pageData = service.findAll(PageRequest.of(page - 1, size));

    ResponsePagination<T> pagination = new ResponsePagination<>(
        pageData.getContent(),
        page,
        pageData.getSize(),
        pageData.getTotalElements(),
        pageData.getTotalPages());

    return ResponseEntity.ok(ApiResponseData.success("Fetched page successfully", pagination));
  }

  @DeleteMapping("/batch")
  public ResponseEntity<ApiResponseData<Void>> deleteAll(@RequestBody List<ID> ids) {
    service.deleteAll(ids);
    return ResponseEntity.ok(ApiResponseData.<Void>success("Deleted all successfully", null));
  }
}
