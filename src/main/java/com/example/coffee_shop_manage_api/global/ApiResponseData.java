package com.example.coffee_shop_manage_api.global;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseData<T> {

 private String message;
 private T data;
 private int statusCode;

 public static <T> ApiResponseData<T> success(T data) {
  return new ApiResponseData<>("Operation successful", data, 200);
 }

 public static <T> ApiResponseData<T> success(String message, T data) {
  return new ApiResponseData<>( message, data, 200);
 }

 public static <T> ApiResponseData<T> success(String message, T data, int statusCode) {
  return new ApiResponseData<>( message, data, statusCode);
 }

 public static <T> ApiResponseData<T> error(String message) {
  return new ApiResponseData<>( message, null, 400);
 }

 public static <T> ApiResponseData<T> error(String message, int statusCode) {
  return new ApiResponseData<>( message, null, statusCode);
 }

 public static <T> ApiResponseData<T> error(String message, T data, int statusCode) {
  return new ApiResponseData<>( message, data, statusCode);
 }

 public static <T> ApiResponseData<T> notFound(String message) {
  return new ApiResponseData<>( message, null, 404);
 }

 public static <T> ApiResponseData<T> unauthorized(String message) {
  return new ApiResponseData<>(message, null, 401);
 }

 public static <T> ApiResponseData<T> forbidden(String message) {
  return new ApiResponseData<>( message, null, 403);
 }

 public static <T> ApiResponseData<T> internalError(String message) {
  return new ApiResponseData<>( message, null, 500);
 }
}
