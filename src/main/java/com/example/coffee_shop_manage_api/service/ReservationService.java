package com.example.coffee_shop_manage_api.service;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.Reservation;
import com.example.coffee_shop_manage_api.repository.ReservationRepository;

@Service
public class ReservationService extends AbstractCommonService<Reservation, String> {
    
    public ReservationService(ReservationRepository reservationRepository) {
        super(reservationRepository);
    }
}
