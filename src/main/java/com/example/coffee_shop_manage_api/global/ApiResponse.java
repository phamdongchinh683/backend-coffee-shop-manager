package com.example.coffee_shop_manage_api.global;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

 private boolean success;
 private String message;
 private T data;
 private int statusCode;

 public static <T> ApiResponse<T> success(T data) {
  return new ApiResponse<>(true, "Operation successful", data, 200);
 }

 public static <T> ApiResponse<T> success(String message, T data) {
  return new ApiResponse<>(true, message, data, 200);
 }

 public static <T> ApiResponse<T> success(String message, T data, int statusCode) {
  return new ApiResponse<>(true, message, data, statusCode);
 }

 // Error responses
 public static <T> ApiResponse<T> error(String message) {
  return new ApiResponse<>(false, message, null, 400);
 }

 public static <T> ApiResponse<T> error(String message, int statusCode) {
  return new ApiResponse<>(false, message, null, statusCode);
 }

 public static <T> ApiResponse<T> error(String message, T data, int statusCode) {
  return new ApiResponse<>(false, message, data, statusCode);
 }

 public static <T> ApiResponse<T> notFound(String message) {
  return new ApiResponse<>(false, message, null, 404);
 }

 public static <T> ApiResponse<T> unauthorized(String message) {
  return new ApiResponse<>(false, message, null, 401);
 }

 public static <T> ApiResponse<T> forbidden(String message) {
  return new ApiResponse<>(false, message, null, 403);
 }

 public static <T> ApiResponse<T> internalError(String message) {
  return new ApiResponse<>(false, message, null, 500);
 }
}
