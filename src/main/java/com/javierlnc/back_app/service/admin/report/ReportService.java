package com.javierlnc.back_app.service.admin.report;

import com.javierlnc.back_app.dto.ReportDataDTO;
import com.javierlnc.back_app.dto.ReportRequestDTO;
import com.javierlnc.back_app.dto.ReportTechnicianDTO;
import com.javierlnc.back_app.dto.TicketDataDTO;

import java.util.List;

public interface ReportService {
    byte[] generateReport(ReportRequestDTO request);
    byte[] generateReportTechnicican(ReportRequestDTO request);
    byte[] generateReportByUser(ReportRequestDTO request);
}
