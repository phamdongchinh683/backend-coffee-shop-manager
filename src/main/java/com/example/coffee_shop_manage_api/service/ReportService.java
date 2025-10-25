package com.example.coffee_shop_manage_api.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.Report;
import com.example.coffee_shop_manage_api.repository.OrderItemRepository;
import com.example.coffee_shop_manage_api.repository.OrderRepository;
import com.example.coffee_shop_manage_api.repository.ReportRepository;
import com.example.coffee_shop_manage_api.repository.ReservationRepository;

@Service
public class ReportService extends AbstractCommonService<Report, String> {
    private final ReportRepository reportRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;

    public ReportService(ReportRepository reportRepository, OrderItemRepository orderItemRepository,
            OrderRepository orderRepository, ReservationRepository reservationRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.reservationRepository = reservationRepository;
    }

    public Report findReportByReportDate(LocalDate reportDate) {
        return reportRepository.findReportByReportDate(reportDate).orElse(null);
    }

    public Report generateRecordByDate(LocalDate reportDate) {
        int totalOrders = orderRepository.countByOrderDate(reportDate);
        BigDecimal totalRevenue = orderItemRepository.getTotalRevenueByDate(reportDate);
        Integer totalCustomerCount = reservationRepository.countByReservationDate(reportDate);

        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }

        int totalCustomer = (totalCustomerCount != null) ? totalCustomerCount : 0;

        Report report = new Report();
        report.setReportDate(reportDate);
        report.setTotalOrders(totalOrders);
        report.setTotalRevenue(totalRevenue);
        report.setTotalCustomer(totalCustomer);
        return reportRepository.save(report);
    }

    public Integer countGuest() {
        return reservationRepository.countByReservationDate(LocalDate.now());
    }

    public BigDecimal totalRevenue() {
        return orderItemRepository.getTotalRevenueByDate(LocalDate.now());
    }

}
