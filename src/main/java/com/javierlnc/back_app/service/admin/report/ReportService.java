package com.javierlnc.back_app.service.admin.report;

import com.javierlnc.back_app.dto.ReportRequestDTO;


public interface ReportService {
    byte[] generateReport(ReportRequestDTO request);
    byte[] generateReportTechnicican(ReportRequestDTO request);
    byte[] generateReportByUser(ReportRequestDTO request);
}
