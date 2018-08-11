package com.tsystems.controller.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.dto.TicketDTO;
import com.tsystems.utils.ConverterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

public class ITextPdfView extends AbstractITextPdfView {

    private static final BaseColor BRAND_PINK = new BaseColor(255, 90, 95, 60);
    private static final BaseColor BRAND_GREY = new BaseColor(221, 221, 221, 230);
    private static final Font BRAND_BIG = FontFactory.getFont(FontFactory.COURIER_BOLD, 24);
    private static final Font BRAND_TABLE_HEADER = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
    private static final Font BRAND_SMALL = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Logo
        URL imageUrl = getClass().getResource("/images/rails-logo-pdf.jpg");
        Image logo = Image.getInstance(imageUrl);
        logo.setAlignment(Image.ALIGN_CENTER);
        document.add(logo);

        TicketDTO ticket = (TicketDTO) model.get("ticket");

        Paragraph stationFrom = new Paragraph("From: " + ticket.getStation_from().getName());
        document.add(stationFrom);
        Paragraph stationTo = new Paragraph("To: " + ticket.getStation_to().getName());
        document.add(stationTo);

        Paragraph route = new Paragraph("ROUTE", BRAND_BIG);
        route.setAlignment(Element.ALIGN_CENTER);
        route.setSpacingBefore(4f);
        route.setSpacingAfter(32f);
        document.add(route);

        PdfPTable routeTable = new PdfPTable(4);
        addTableHeader(routeTable);
        java.util.List<ScheduleDTO> schedules = (java.util.List<ScheduleDTO>) model.get("schedules");
        for (ScheduleDTO schedule : schedules) {

            boolean userStation = false;
            if (schedule.getStationDto().getName().equals(ticket.getStation_from().getName())) {
                userStation = true;
            } else if (schedule.getStationDto().getName().equals(ticket.getStation_to().getName())) {
                userStation = true;
            }

            // Station cell
            PdfPCell station = new PdfPCell();
            station.setPhrase(new Phrase(schedule.getStationDto().getName(), BRAND_SMALL));
            station.setHorizontalAlignment(Element.ALIGN_CENTER);
            station.setVerticalAlignment(Element.ALIGN_MIDDLE);
            station.setPaddingTop(4);
            station.setPaddingBottom(8);
            if (userStation)
                station.setBackgroundColor(BRAND_PINK);
            routeTable.addCell(station);

            // Time arrival cell
            PdfPCell timeArrival = new PdfPCell();
            if (schedule.getTime_arrival() != null) {
                timeArrival.setPhrase(new Phrase(ConverterUtil.getFormattedString(schedule.getTime_arrival()), BRAND_SMALL));
            } else {
                timeArrival.setPhrase(new Phrase("-", BRAND_SMALL));
            }
            timeArrival.setHorizontalAlignment(Element.ALIGN_CENTER);
            timeArrival.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (userStation)
                timeArrival.setBackgroundColor(BRAND_PINK);
            routeTable.addCell(timeArrival);

            // Time stop cell
            PdfPCell timeStop = new PdfPCell();
            if (schedule.getTime_stop() == null) {
                timeStop.setPhrase(new Phrase("-", BRAND_SMALL));
            } else {
                timeStop.setPhrase(new Phrase(schedule.getTime_stop(), BRAND_SMALL));
            }
            timeStop.setHorizontalAlignment(Element.ALIGN_CENTER);
            timeStop.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (userStation)
                timeStop.setBackgroundColor(BRAND_PINK);
            routeTable.addCell(timeStop);

            // Time departure cell
            PdfPCell timeDeparture = new PdfPCell();
            if (schedule.getTime_departure() != null) {
                timeDeparture.setPhrase(new Phrase(ConverterUtil.getFormattedString(schedule.getTime_departure()), BRAND_SMALL));
            } else {
                timeDeparture.setPhrase(new Phrase("-", BRAND_SMALL));
            }
            timeDeparture.setHorizontalAlignment(Element.ALIGN_CENTER);
            timeDeparture.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (userStation)
                timeDeparture.setBackgroundColor(BRAND_PINK);
            routeTable.addCell(timeDeparture);
        }
        document.add(routeTable);

    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Station", "Arriving", "Time stop", "Departure")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPaddingTop(8);
                    header.setPaddingBottom(12);
                    header.setBackgroundColor(BRAND_GREY);
                    header.setPhrase(new Phrase(columnTitle, BRAND_TABLE_HEADER));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(header);
                });
    }

}
