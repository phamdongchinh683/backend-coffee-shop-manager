package com.example.coffee_shop_manage_api.controller.report;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.global.ApiResponseData;
import com.example.coffee_shop_manage_api.model.Report;
import com.example.coffee_shop_manage_api.service.ReportService;

@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasRole('ADMIN')")

public class ReportController extends AbstractCommonController<Report, String> {
 private final ReportService reportService;

 public ReportController(ReportService reportService) {
  super(reportService);
  this.reportService = reportService;
 }

 @GetMapping(params = { "date" })
 public ResponseEntity<ApiResponseData<Report>> findReportByDate(@RequestParam LocalDate date) {
  Report reportResponse = reportService.findReportByReportDate(date);
  return ResponseEntity.ok(ApiResponseData.success("Report ", reportResponse));
 }

 @PostMapping("/generate")
 public ResponseEntity<ApiResponseData<Report>> create(@RequestBody LocalDate date) {
  Report created = reportService.generateRecordByDate(date);
  return ResponseEntity.status(201).body(ApiResponseData.success("Created successfully", created, 201));
 }

 @GetMapping("/total-guest")
 public ResponseEntity<ApiResponseData<Integer>> countGuest() {
  Integer count = reportService.countGuest();
  return ResponseEntity.ok(ApiResponseData.success("Count guest successfully", count));
 }

 @GetMapping("/total-revenue")
 public ResponseEntity<ApiResponseData<BigDecimal>> totalRevenue() {
  BigDecimal totalRevenue = reportService.totalRevenue();
  return ResponseEntity.ok(ApiResponseData.success("Total revenue successfully", totalRevenue));
 }
}