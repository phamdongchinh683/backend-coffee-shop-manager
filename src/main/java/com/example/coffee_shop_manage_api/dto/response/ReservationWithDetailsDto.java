package com.example.coffee_shop_manage_api.dto.response;

import java.time.LocalDateTime;

import com.example.coffee_shop_manage_api.global.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationWithDetailsDto {
 String id;
 ReservationStatus status;
 LocalDateTime reservationTime;
 Integer numberOfGuests;
 LocalDateTime createdAt;
 LocalDateTime updatedAt;

 // User details
 String userId;
 String userFullName;
 String userUsername;
 String userPhoneNumber;

 // Table details
 String tableId;
 Short tableNumber;
}
