package com.example.coffee_shop_manage_api.controller.menu;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.model.Menu;
import com.example.coffee_shop_manage_api.service.MenuService;

@RestController
@RequestMapping("/api/menus/v1")
public class MenuController extends AbstractCommonController<Menu, String> {

 public MenuController(MenuService menuService) {
  super(menuService);
 }
}
