package com.example.coffee_shop_manage_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
