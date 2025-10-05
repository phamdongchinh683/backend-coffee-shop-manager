package com.example.coffee_shop_manage_api.service;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.Menu;
import com.example.coffee_shop_manage_api.repository.MenuRepository;

@Service
public class MenuService extends AbstractCommonService<Menu, String> {
    
    public MenuService(MenuRepository menuRepository) {
        super(menuRepository);
    }
}
