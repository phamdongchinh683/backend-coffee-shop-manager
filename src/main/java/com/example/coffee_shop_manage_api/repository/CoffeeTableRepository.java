package com.example.coffee_shop_manage_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.coffee_shop_manage_api.global.PaymentStatus;
import com.example.coffee_shop_manage_api.global.TableStatus;
import com.example.coffee_shop_manage_api.model.CoffeeTable;

@Repository
public interface CoffeeTableRepository extends JpaRepository<CoffeeTable, String> {
    Optional<CoffeeTable> findByTableNumber(Short tableNumber);

    @Modifying
    @Query("UPDATE CoffeeTable t SET t.status = :status, t.paymentStatus = :paymentStatus WHERE t.id = :id")
    void updateTableById(@Param("id") String id, @Param("status") TableStatus status,
            @Param("paymentStatus") PaymentStatus paymentStatus);
}
