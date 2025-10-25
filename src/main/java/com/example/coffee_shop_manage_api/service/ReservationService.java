package com.example.coffee_shop_manage_api.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.dto.request.ReservationRequestDto;
import com.example.coffee_shop_manage_api.dto.response.ReservationWithDetailsDto;
import com.example.coffee_shop_manage_api.global.PaymentStatus;
import com.example.coffee_shop_manage_api.global.ReservationStatus;
import com.example.coffee_shop_manage_api.global.TableStatus;
import com.example.coffee_shop_manage_api.kafka.producer.ReportProducer;
import com.example.coffee_shop_manage_api.kafka.producer.ReservationProducer;
import com.example.coffee_shop_manage_api.kafka.producer.TableStatusProducer;
import com.example.coffee_shop_manage_api.model.Reservation;
import com.example.coffee_shop_manage_api.repository.CoffeeTableRepository;
import com.example.coffee_shop_manage_api.repository.ReservationRepository;
import com.example.coffee_shop_manage_api.repository.UserRepository;

@Service
public class ReservationService extends AbstractCommonService<Reservation, String> {

    private final ReservationProducer reservationProducer;
    private final CoffeeTableRepository coffeeTableRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final TableStatusProducer tableStatusProducer;
    private final ReportProducer reportProducer;

    public ReservationService(ReservationRepository reservationRepository, ReservationProducer reservationProducer,
            CoffeeTableRepository coffeeTableRepository, UserRepository userRepository,
            TableStatusProducer tableStatusProducer,
            ReportProducer reportProducer) {
        super(reservationRepository);
        this.reservationProducer = reservationProducer;
        this.coffeeTableRepository = coffeeTableRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.tableStatusProducer = tableStatusProducer;
        this.reportProducer = reportProducer;
    }

    public Reservation sendMessage(ReservationRequestDto request) {
        try {
            if (request == null) {
                throw new IllegalArgumentException("Request is required");
            }
            if (request.getTableId() == null || request.getTableId().trim().isEmpty()) {
                throw new IllegalArgumentException("Table ID is required");
            }
            if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
                throw new IllegalArgumentException("User ID is required");
            }

            if (request.getReservationTime() == null) {
                throw new IllegalArgumentException("Reservation time is required");
            }

            if (request.getNumberOfGuests() == null || request.getNumberOfGuests() <= 0) {
                throw new IllegalArgumentException("Number of guests must be positive");
            }

            Reservation reservation = new Reservation();
            reservation.setTable(coffeeTableRepository.findById(request.getTableId()).orElseThrow(
                    () -> new IllegalArgumentException("Table not found with ID: " + request.getTableId())));
            reservation.setUser(userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId())));
            reservation.setReservationTime(request.getReservationTime());
            reservation.setNumberOfGuests(request.getNumberOfGuests());

            Reservation savedReservation = super.create(reservation);

            try {
                reservationProducer.sendReservation(savedReservation);
            } catch (Exception e) {
                // Log the error but don't fail the transaction
                System.err.println("Failed to send reservation message to Kafka: " + e.getMessage());
            }

            return savedReservation;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create reservation: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void verifyReservation(String id) {
        try {
            Reservation reservation = reservationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + id));

            // Update reservation status
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservationRepository.updateReservationStatusById(id, ReservationStatus.CONFIRMED);

            coffeeTableRepository.updateTableById(reservation.getTable().getId(),
                    TableStatus.OCCUPIED,
                    PaymentStatus.PENDING);

            try {
                tableStatusProducer.sendTableStatus(
                        reservation.getTable().getTableNumber(),
                        TableStatus.OCCUPIED.name(),
                        PaymentStatus.PENDING.name());
            } catch (Exception e) {
                System.err.println("Failed to send table status message to Kafka: " + e.getMessage());
            }

            try {
                reservationProducer.sendReservation(reservation);
            } catch (Exception e) {
                System.err.println("Failed to send reservation message to Kafka: " + e.getMessage());
            }

            try {
                reportProducer.sendTotalCustomer(reservation.getNumberOfGuests());
            } catch (Exception e) {
                System.err.println("Failed to send report message to Kafka: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify reservation: " + e.getMessage(), e);
        }
    }

    public org.springframework.data.domain.Page<ReservationWithDetailsDto> findAllWithDetails(int page, int size) {
        return reservationRepository.findAllWithDetails(PageRequest.of(page - 1, size));
    }

    public org.springframework.data.domain.Page<ReservationWithDetailsDto> findByUserIdWithDetails(String userId,
            int page, int size) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        return reservationRepository.findByUserIdWithDetails(userId, PageRequest.of(page - 1, size));
    }
}
