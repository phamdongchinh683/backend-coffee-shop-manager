package com.example.coffee_shop_manage_api.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationRequestDto {


 @NotNull(message = "Table ID is required")
 String reservationId;

 @NotNull(message = "Table ID is required")
 String tableId;

 @NotNull(message = "User ID is required")
 String userId;

 @NotNull(message = "Reservation time is required")
 @Future(message = "Reservation time must be in the future")
 LocalDateTime reservationTime;

 @NotNull(message = "Number of guests is required")
 @Positive(message = "Number of guests must be positive")
 Integer numberOfGuests;
}
