package com.example.coffee_shop_manage_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coffee_shop_manage_api.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
}
