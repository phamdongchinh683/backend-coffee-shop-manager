package com.example.coffee_shop_manage_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.example.coffee_shop_manage_api.global.OrderStatus;

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
 @JsonIgnore
 CoffeeTable table;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_id", nullable = false)
 @JsonIgnore
 User user;

 @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
 BigDecimal totalAmount;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
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
