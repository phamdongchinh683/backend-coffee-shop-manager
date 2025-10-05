package com.example.coffee_shop_manage_api.service;

import org.springframework.stereotype.Service;

import com.example.coffee_shop_manage_api.common.AbstractCommonService;
import com.example.coffee_shop_manage_api.model.Report;
import com.example.coffee_shop_manage_api.repository.ReportRepository;

@Service
public class ReportService extends AbstractCommonService<Report, String> {
    
    public ReportService(ReportRepository reportRepository) {
        super(reportRepository);
    }
}
