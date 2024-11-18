package com.javierlnc.back_app.controller.admin;

import com.javierlnc.back_app.dto.ReportDataDTO;
import com.javierlnc.back_app.dto.ReportRequestDTO;
import com.javierlnc.back_app.dto.ReportTechnicianDTO;
import com.javierlnc.back_app.dto.TicketDataDTO;
import com.javierlnc.back_app.service.admin.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ReportController {
private final ReportService reportService;


@PostMapping("/report")
public ResponseEntity<?> generateReport(@RequestParam ReportRequestDTO requestDTO){

        try {
            String type = requestDTO.getReportType();
            switch (type){
                case "general":
                    ReportDataDTO reportGeneral = reportService.generateReport(requestDTO);
                    return ResponseEntity.ok(reportGeneral);
                case "tecnico":
                    ReportTechnicianDTO reportTechnicican = reportService.generateReportTechnicican(requestDTO);
                    return ResponseEntity.ok(reportTechnicican);
                case"especifico":
                    ReportDataDTO reportSpecific = reportService.generateReportByUser(requestDTO);
                    return  ResponseEntity.ok(reportSpecific);
                default:
                    throw new RuntimeException("No se encuentra el tipo de reporte");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

     }
}
