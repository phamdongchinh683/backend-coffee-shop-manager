package com.example.coffee_shop_manage_api.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.model.User;
import com.example.coffee_shop_manage_api.service.UserService;

@RestController
@RequestMapping("/api/users/v1")
@PreAuthorize("hasRole('ADMIN')")
public class UserController extends AbstractCommonController<User, String> {
 public UserController(UserService userService) {
  super(userService);
 }
}