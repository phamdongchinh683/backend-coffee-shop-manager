package com.example.coffee_shop_manage_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.coffee_shop_manage_api.global.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "orders")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

 @Id
 @GeneratedValue(strategy = GenerationType.UUID)
 String id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "table_id", nullable = false)
 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
 @NotNull(message = "Table is required")
 CoffeeTable table;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_id", nullable = false)
 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
 @NotNull(message = "User is required")
 User user;

 @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
 @NotNull(message = "Total amount is required")
 @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
 BigDecimal totalAmount;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 @NotNull(message = "Order status is required")
 OrderStatus status = OrderStatus.OPEN;

 @CreationTimestamp
 @Column(name = "created_at", updatable = false)
 LocalDateTime createdAt;

 @UpdateTimestamp
 @Column(name = "updated_at")
 LocalDateTime updatedAt;

 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
 @JsonIgnore
 List<OrderItem> orderItems;

}
