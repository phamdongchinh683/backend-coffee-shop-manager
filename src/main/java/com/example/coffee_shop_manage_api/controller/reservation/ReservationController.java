package com.example.coffee_shop_manage_api.controller.reservation;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.dto.request.ReservationRequestDto;
import com.example.coffee_shop_manage_api.dto.response.ReservationWithDetailsDto;
import com.example.coffee_shop_manage_api.global.ApiResponseData;
import com.example.coffee_shop_manage_api.global.ResponsePagination;
import com.example.coffee_shop_manage_api.model.Reservation;
import com.example.coffee_shop_manage_api.service.ReservationService;

@RestController
@RequestMapping("/api/v1/reservations")
@PreAuthorize("hasRole('GUEST')")
public class ReservationController extends AbstractCommonController<Reservation, String> {
  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    super(reservationService);
    this.reservationService = reservationService;
  }

  @PostMapping("/send-message")
  public ResponseEntity<ApiResponseData<Reservation>> postMessage(@RequestBody ReservationRequestDto request) {
    Reservation savedReservation = reservationService.sendMessage(request);
    return ResponseEntity.status(201).body(ApiResponseData.success("Message sent successfully", savedReservation, 201));
  }

  @PutMapping("/verify-reservation/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponseData<Void>> verifyReservation(@PathVariable String id) {
    reservationService.verifyReservation(id);
    return ResponseEntity.status(201)
        .body(ApiResponseData.success("Reservation verified successfully", null, 201));
  }

  @GetMapping("/with-details")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponseData<ResponsePagination<ReservationWithDetailsDto>>> getPageWithDetails(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<ReservationWithDetailsDto> pageData = reservationService.findAllWithDetails(page, size);

    ResponsePagination<ReservationWithDetailsDto> pagination = new ResponsePagination<ReservationWithDetailsDto>(
        pageData.getContent(),
        page,
        pageData.getSize(),
        pageData.getTotalElements(),
        pageData.getTotalPages());

    return ResponseEntity.ok(ApiResponseData.success("Fetched page successfully", pagination));
  }

  @GetMapping("/user/{userId}")
  @PreAuthorize("hasRole('GUEST') or hasRole('ADMIN')")
  public ResponseEntity<ApiResponseData<ResponsePagination<ReservationWithDetailsDto>>> getReservationsByUserId(
      @PathVariable String userId,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<ReservationWithDetailsDto> pageData = reservationService.findByUserIdWithDetails(userId, page, size);

    ResponsePagination<ReservationWithDetailsDto> pagination = new ResponsePagination<ReservationWithDetailsDto>(
        pageData.getContent(),
        page,
        pageData.getSize(),
        pageData.getTotalElements(),
        pageData.getTotalPages());

    return ResponseEntity.ok(ApiResponseData.success("Fetched user reservations successfully", pagination));
  }
}
