package com.example.coffee_shop_manage_api.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.coffee_shop_manage_api.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

 @Query("SELECT COALESCE(SUM(o.subtotal), 0) FROM OrderItem o WHERE DATE(o.createdAt) = :date")
 BigDecimal getTotalRevenueByDate(@Param("date") LocalDate date);
}
