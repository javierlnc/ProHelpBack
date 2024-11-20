package com.javierlnc.back_app.service.admin.report;

import com.itextpdf.text.DocumentException;
import com.javierlnc.back_app.dto.*;
import com.javierlnc.back_app.entity.Report;
import com.javierlnc.back_app.repository.ReportRepository;
import com.javierlnc.back_app.utils.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final PdfGenerator pdfGenerator;


    public byte[] generateReport(ReportRequestDTO request){
        List<TicketDataDTO> data = fetchReportData(request);
        try{
            ReportDataDTO dto = new ReportDataDTO();
            dto.setTitle("Reporte General");
            dto.setStartDate(request.getStarDate());
            dto.setEndDate(request.getEndDate());
            dto.setTickets(data);
            convertDTOToReport(dto.getTitle(),dto.getStartDate(),dto.getEndDate());
            byte [] pdfBytes = pdfGenerator.generateReportPdf(dto);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=reporte.pdf");
            headers.add("Content-Type", "application/pdf");
            return pdfBytes;


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
public byte[] generateReportTechnicican(ReportRequestDTO request){
        List<TechnicianReportDTO> data = reportRepository.findClosedTicketsInRangeWithResolutionByUsers(request.getStarDate(),request.getEndDate());
        try{
            ReportTechnicianDTO dto = new ReportTechnicianDTO();
            dto.setTitle("reporte General de tecnicos");
            dto.setStartDate(request.getStarDate());
            dto.setEndDate(request.getEndDate());
            dto.setTechnicians(data);
            convertDTOToReport(dto.getTitle(), dto.getStartDate(), dto.getEndDate());
            byte [] pdfBytes = pdfGenerator.generateReportTechnician(dto);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=reporte.pdf");
            headers.add("Content-Type", "application/pdf");
            return pdfBytes;
    } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
}
    public byte[] generateReportByUser(ReportRequestDTO request){
        List<TicketDataDTO> data = reportRepository.findClosedTicketsInRangeWithAssignedTechnician(request.getStarDate(),request.getEndDate(),request.getAssignedTechnicianId());
        try{
            ReportDataDTO dto = new ReportDataDTO();
            dto.setTitle("Reporte General");
            dto.setStartDate(request.getStarDate());
            dto.setEndDate(request.getEndDate());
            dto.setTickets(data);
            convertDTOToReport(dto.getTitle(),dto.getStartDate(),dto.getEndDate());
            byte [] pdfBytes = pdfGenerator.generateReportPdf(dto);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=reporte.pdf");
            headers.add("Content-Type", "application/pdf");
            return pdfBytes;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TicketDataDTO> fetchReportData(ReportRequestDTO requestDTO){
        return  reportRepository.findClosedTicketsInRangeWithResolution(requestDTO.getStarDate(), requestDTO.getEndDate());
    }
    private void convertDTOToReport(String title, LocalDateTime startDate, LocalDateTime endDate){
        Report report = new Report();
        report.setTitle(title);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        reportRepository.save(report);
    }

}
