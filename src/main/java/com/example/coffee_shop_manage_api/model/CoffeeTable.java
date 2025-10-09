package com.example.coffee_shop_manage_api.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.coffee_shop_manage_api.global.PaymentStatus;
import com.example.coffee_shop_manage_api.global.TableStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tables")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoffeeTable {

 @Id
 @GeneratedValue(strategy = GenerationType.UUID)
 String id;

 @Column(name = "table_number", nullable = false)
 @NotNull(message = "Table number is required")
 @Positive(message = "Table number must be positive")
 Short tableNumber;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 @NotNull(message = "Table status is required")
 TableStatus status = TableStatus.AVAILABLE;

 @Enumerated(EnumType.STRING)
 @Column(name = "payment_status", nullable = false)
 @NotNull(message = "Payment status is required")
 PaymentStatus paymentStatus = PaymentStatus.PENDING;

 @CreationTimestamp
 @Column(name = "created_at", updatable = false)
 LocalDateTime createdAt;

 @UpdateTimestamp
 @Column(name = "updated_at")
 LocalDateTime updatedAt;

 @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 List<Order> orders;

 @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
 @JsonIgnore
 List<Reservation> reservations;
}
