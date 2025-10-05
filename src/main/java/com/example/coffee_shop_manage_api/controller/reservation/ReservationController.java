package com.example.coffee_shop_manage_api.controller.reservation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.model.Reservation;
import com.example.coffee_shop_manage_api.service.ReservationService;

@RestController
@RequestMapping("/api/reservations/v1")
public class ReservationController extends AbstractCommonController<Reservation, String> {

 public ReservationController(ReservationService reservationService) {
  super(reservationService);
 }
}
