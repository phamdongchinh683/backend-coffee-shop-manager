package com.example.coffee_shop_manage_api.service;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.CoffeeTable;
import com.example.coffee_shop_manage_api.repository.CoffeeTableRepository;

@Service
public class CoffeeTableService extends AbstractCommonService<CoffeeTable, String> {
    
    public CoffeeTableService(CoffeeTableRepository coffeeTableRepository) {
        super(coffeeTableRepository);
    }
}
