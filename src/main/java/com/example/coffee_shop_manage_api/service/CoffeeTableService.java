package com.example.coffee_shop_manage_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.kafka.producer.TableStatusProducer;
import com.example.coffee_shop_manage_api.model.CoffeeTable;
import com.example.coffee_shop_manage_api.repository.CoffeeTableRepository;

@Service
public class CoffeeTableService extends AbstractCommonService<CoffeeTable, String> {

    private final TableStatusProducer tableStatusProducer;
    private final CoffeeTableRepository coffeeTableRepository;

    public CoffeeTableService(CoffeeTableRepository coffeeTableRepository, TableStatusProducer tableStatusProducer) {
        super(coffeeTableRepository);
        this.tableStatusProducer = tableStatusProducer;
        this.coffeeTableRepository = coffeeTableRepository;
    }

    @Transactional
    public CoffeeTable updateStatusByTableId(String tableId, CoffeeTable request) {
        CoffeeTable table = this.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));

        coffeeTableRepository.updateTableById(tableId, request.getStatus(), request.getPaymentStatus());
        table.setStatus(request.getStatus());
        table.setPaymentStatus(request.getPaymentStatus());

        tableStatusProducer.sendTableStatus(table.getTableNumber(), request.getStatus().name(),
                request.getPaymentStatus().name());

        return table;
    }

}
