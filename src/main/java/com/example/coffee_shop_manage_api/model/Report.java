package com.example.coffee_shop_manage_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.DecimalMin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "reports")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "report_date", nullable = false)
    @NotNull(message = "Report date is required")
    LocalDate reportDate;

    @Column(name = "total_customers", nullable = false)
    @NotNull(message = "Total customers is required")
    @PositiveOrZero(message = "Total customers must be zero or positive")
    Integer totalCustomers;

    @Column(name = "total_orders", nullable = false)
    @NotNull(message = "Total orders is required")
    @PositiveOrZero(message = "Total orders must be zero or positive")
    Integer totalOrders;

    @Column(name = "total_revenue", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "Total revenue is required")
    @DecimalMin(value = "0.00", message = "Total revenue must be zero or positive")
    BigDecimal totalRevenue;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
