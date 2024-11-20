package com.javierlnc.back_app.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.javierlnc.back_app.dto.ReportDataDTO;
import com.javierlnc.back_app.dto.TicketDataDTO;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;
@Component
public class PdfGenerator {
    public byte[] generateReportPdf(ReportDataDTO reportData) throws  DocumentException{
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("Reporte: " + reportData.getTitle()));
            document.add(new Paragraph("Desde: "+reportData.getStartDate()+" Hasta: "+reportData.getEndDate()));
            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            tableHeader(table);
            fillTableWithData(table, reportData.getTickets());
            document.add(table);


        } finally {
            document.close();
        }
        return  outputStream.toByteArray();
    }
    private void tableHeader (PdfPTable table){
        Stream.of("id","asunto","Categoría", "Prioridad", "Solicitante","Técnico", "Fecha de Creacíon","Fecha de cierre", "Fecha de resolución", "Cumple").forEach(title -> {
            PdfPCell header = new PdfPCell();
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(title));
            table.addCell(header);
        });
    }
    private void fillTableWithData (PdfPTable table, List<TicketDataDTO> tickets){
        for (TicketDataDTO ticket : tickets){
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(ticket.getSubject());
            table.addCell(ticket.getCategoryName());
            table.addCell(ticket.getPriorityName());
            table.addCell(ticket.getRequesterName());
            table.addCell(ticket.getAssignedTechnicianName());
            table.addCell(ticket.getCreatedDate().toString());
            table.addCell(ticket.getDueDate() != null ? ticket.getDueDate().toString() : "-");
            table.addCell(ticket.getResolutionDate() != null ? ticket.getResolutionDate().toString() : "-");
            table.addCell(ticket.isOverdue() ? "Sí" : "No");
        }
    }
}
