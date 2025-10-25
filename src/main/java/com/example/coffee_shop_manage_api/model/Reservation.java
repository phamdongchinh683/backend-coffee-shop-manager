package com.example.coffee_shop_manage_api.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.coffee_shop_manage_api.global.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "reservations")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {

 @Id
 @GeneratedValue(strategy = GenerationType.UUID)
 String id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "table_id", nullable = false)
 @JsonIgnore
 @NotNull(message = "Table is required")
 CoffeeTable table;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_id", nullable = false)
 @JsonIgnore
 @NotNull(message = "User is required")
 User user;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 @NotNull(message = "Reservation status is required")
 ReservationStatus status = ReservationStatus.PENDING;

 @Column(name = "reservation_time", nullable = false)
 @NotNull(message = "Reservation time is required")
 @Future(message = "Reservation time must be in the future")
 LocalDateTime reservationTime;

 @Column(name = "number_of_guests", nullable = false)
 @NotNull(message = "Number of guests is required")
 Integer numberOfGuests;

 @CreationTimestamp
 @Column(name = "created_at", updatable = false)
 LocalDateTime createdAt;

 @UpdateTimestamp
 @Column(name = "updated_at")
 LocalDateTime updatedAt;

}
