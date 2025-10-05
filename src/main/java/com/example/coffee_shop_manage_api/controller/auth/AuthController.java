package com.example.coffee_shop_manage_api.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.dto.request.UserCreationDto;
import com.example.coffee_shop_manage_api.dto.request.UserLoginDto;
import com.example.coffee_shop_manage_api.global.ApiResponse;
import com.example.coffee_shop_manage_api.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

 private final UserService userService;

 public AuthController(UserService userService) {
  this.userService = userService;
 }

 @PostMapping("/register")
 public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody UserCreationDto userCreationDto) {
  try {
   userService.register(userCreationDto);
   return ResponseEntity.status(201).body(ApiResponse.success("User created successfully", "", 201));
  } catch (RuntimeException e) {
   return ResponseEntity.status(400).body(ApiResponse.error(e.getMessage(), 400));
  }
 }

 @PostMapping("/login")
 public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserLoginDto userLoginDto) {
  try {
   String result = userService.login(userLoginDto);
   return ResponseEntity.status(200).body(ApiResponse.success("Login successful", result, 200));
  } catch (RuntimeException e) {
   return ResponseEntity.status(400).body(ApiResponse.error(e.getMessage(), 400));
  }
 }
}
