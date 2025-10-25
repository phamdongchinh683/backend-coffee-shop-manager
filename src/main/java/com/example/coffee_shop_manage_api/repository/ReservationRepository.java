package com.example.coffee_shop_manage_api.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.coffee_shop_manage_api.dto.response.ReservationWithDetailsDto;
import com.example.coffee_shop_manage_api.global.ReservationStatus;
import com.example.coffee_shop_manage_api.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

 @Query("SELECT COALESCE(SUM(r.numberOfGuests), 0) FROM Reservation r WHERE DATE(r.createdAt) = :reportDate")
 Integer countByReservationDate(@Param("reportDate") LocalDate reportDate);

 @Modifying
 @Transactional
 @Query("UPDATE Reservation r SET r.status = :status WHERE r.id = :id")
 void updateReservationStatusById(@Param("id") String id, @Param("status") ReservationStatus status);

 @Query("SELECT new com.example.coffee_shop_manage_api.dto.response.ReservationWithDetailsDto(" +
   "r.id, r.status, r.reservationTime, r.numberOfGuests, r.createdAt, r.updatedAt, " +
   "u.id, u.fullName, u.username, u.phoneNumber, " +
   "t.id, t.tableNumber) " +
   "FROM Reservation r " +
   "JOIN r.user u " +
   "JOIN r.table t " +
   "ORDER BY r.createdAt DESC")
 Page<ReservationWithDetailsDto> findAllWithDetails(Pageable pageable);

 @Query("SELECT new com.example.coffee_shop_manage_api.dto.response.ReservationWithDetailsDto(" +
   "r.id, r.status, r.reservationTime, r.numberOfGuests, r.createdAt, r.updatedAt, " +
   "u.id, u.fullName, u.username, u.phoneNumber, " +
   "t.id, t.tableNumber) " +
   "FROM Reservation r " +
   "JOIN r.user u " +
   "JOIN r.table t " +
   "WHERE u.id = :userId " +
   "ORDER BY r.createdAt DESC")
 Page<ReservationWithDetailsDto> findByUserIdWithDetails(@Param("userId") String userId, Pageable pageable);
}
