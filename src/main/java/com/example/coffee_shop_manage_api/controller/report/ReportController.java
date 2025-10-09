package com.example.coffee_shop_manage_api.controller.report;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coffee_shop_manage_api.common.AbstractCommonController;
import com.example.coffee_shop_manage_api.model.Report;
import com.example.coffee_shop_manage_api.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/reports/v1")
@PreAuthorize("hasRole('ADMIN')")

public class ReportController extends AbstractCommonController<Report, String> {

 public ReportController(ReportService reportService) {
  super(reportService);
 }
}