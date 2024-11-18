package com.javierlnc.back_app.service.admin.report;

import com.javierlnc.back_app.dto.ReportDataDTO;
import com.javierlnc.back_app.dto.ReportRequestDTO;
import com.javierlnc.back_app.dto.ReportTechnicianDTO;
import com.javierlnc.back_app.dto.TicketDataDTO;

import java.util.List;

public interface ReportService {
    ReportDataDTO generateReport(ReportRequestDTO request);
    ReportTechnicianDTO generateReportTechnicican(ReportRequestDTO request);
    ReportDataDTO generateReportByUser(ReportRequestDTO request);
}
