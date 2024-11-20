package com.javierlnc.back_app.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.javierlnc.back_app.dto.ReportDataDTO;
import com.javierlnc.back_app.dto.ReportTechnicianDTO;
import com.javierlnc.back_app.dto.TechnicianReportDTO;
import com.javierlnc.back_app.dto.TicketDataDTO;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PdfGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
    private static final Font CELL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
    private static final BaseColor HEADER_BG_COLOR = new BaseColor(0, 102, 204);

    public byte[] generateReportPdf(ReportDataDTO reportData) throws DocumentException {
        return generatePdf(reportData.getTitle(), reportData.getStartDate(), reportData.getEndDate(),
                reportData.getTickets(), this::tableHeader, this::fillTableWithData, PageSize.LETTER.rotate());
    }

    public byte[] generateReportTechnician(ReportTechnicianDTO reportData) throws DocumentException {
        return generatePdf(reportData.getTitle(), reportData.getStartDate(), reportData.getEndDate(),
                reportData.getTechnicians(), this::tableTechnicianHeader, this::fillTableTechnician, PageSize.LETTER);
    }

    private byte[] generatePdf(String title, LocalDateTime startDate, LocalDateTime endDate,
                               List<?> dataList, TableHeaderSetter headerSetter, TableDataSetter dataSetter,
                               Rectangle pageSize) throws DocumentException {

        Document document = new Document(pageSize);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.add(new Paragraph("Reporte: " + title, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK)));
            document.add(new Paragraph("Desde: " + startDate.format(DATE_FORMATTER) + " Hasta: " + endDate.format(DATE_FORMATTER), FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.DARK_GRAY)));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(getColumnCount(dataList));
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            headerSetter.setTableHeader(table);
            dataSetter.setTableData(table, dataList);

            document.add(table);
        } finally {
            document.close();
        }
        return outputStream.toByteArray();
    }

    private int getColumnCount(List<?> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return 0;
        }
        Object firstElement = dataList.get(0);
        if (firstElement instanceof TechnicianReportDTO) {
            return 5;
        } else if (firstElement instanceof TicketDataDTO) {
            return 10;
        }
        throw new IllegalArgumentException("Tipo de datos desconocido en la lista");
    }

    private void tableTechnicianHeader(PdfPTable table) {
        String[] headers = {"id", "Nombre Técnico", "Solicitudes Totales", "Solicitudes Vencidas", "ANS"};
        createTableHeader(table, headers);
    }

    private void tableHeader(PdfPTable table) {
        String[] headers = {"id", "Asunto", "Categoría", "Prioridad", "Solicitante", "Técnico", "Fecha de Creación", "Fecha de Cierre", "Fecha de Resolución", "ANS"};
        createTableHeader(table, headers);
    }

    private void createTableHeader(PdfPTable table, String[] headers) {
        Stream.of(headers).forEach(title -> {
            PdfPCell header = new PdfPCell(new Phrase(title, HEADER_FONT));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setBackgroundColor(HEADER_BG_COLOR);
            header.setPadding(8);
            header.setBorderWidth(1);
            table.addCell(header);
        });
    }

    private void fillTableWithData(PdfPTable table, List<TicketDataDTO> tickets) {
        tickets.forEach(ticket -> {
            table.addCell(createStyledCell(ticket.getId().toString()));
            table.addCell(createStyledCell(ticket.getSubject()));
            table.addCell(createStyledCell(ticket.getCategoryName()));
            table.addCell(createStyledCell(ticket.getPriorityName()));
            table.addCell(createStyledCell(ticket.getRequesterName()));
            table.addCell(createStyledCell(ticket.getAssignedTechnicianName()));
            table.addCell(createStyledCell(formatDate(ticket.getCreatedDate())));
            table.addCell(createStyledCell(formatDate(ticket.getDueDate())));
            table.addCell(createStyledCell(formatDate(ticket.getResolutionDate())));
            table.addCell(createStyledCell(ticket.isOverdue() ? "Sí" : "No"));
        });
    }

    private void fillTableTechnician(PdfPTable table, List<TechnicianReportDTO> technicians) {
        technicians.forEach(technician -> {
            table.addCell(createStyledCell(technician.getUserId().toString()));
            table.addCell(createStyledCell(technician.getUserName()));
            table.addCell(createStyledCell(technician.getTotalTickets().toString()));
            table.addCell(createStyledCell(technician.getOverdueTickets().toString()));
            table.addCell(createStyledCell(technician.getOnTimeTickets().toString()));
        });
    }

    private PdfPCell createStyledCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, CELL_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        return cell;
    }

    private String formatDate(LocalDateTime date) {
        return date != null ? date.format(DATE_FORMATTER) : "-";
    }

    @FunctionalInterface
    private interface TableHeaderSetter {
        void setTableHeader(PdfPTable table);
    }

    @FunctionalInterface
    private interface TableDataSetter<T> {
        void setTableData(PdfPTable table, List<T> dataList);
    }
}
