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

import com.example.coffee_shop_manage_api.global.OrderStatus;
import com.example.coffee_shop_manage_api.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

  @Query("""
          SELECT o
          FROM Order o
          LEFT JOIN FETCH o.orderItems oi
          LEFT JOIN FETCH oi.menu
          WHERE o.id = :orderId
      """)
  Order findOrderWithItems(@Param("orderId") String orderId);

  @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = :reportDate")
  int countByOrderDate(@Param("reportDate") LocalDate reportDate);

  @Query("""
          SELECT o
          FROM Order o
          WHERE o.user.id = :userId
          ORDER BY o.createdAt DESC
      """)
  Page<Order> findByUserId(@Param("userId") String userId, Pageable pageable);

  @Modifying
  @Transactional
  @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
  void updateOrderStatusById(@Param("id") String id, @Param("status") OrderStatus status);
}
