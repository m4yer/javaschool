package com.tsystems.controller;

import com.tsystems.client.ScheduleService;
import com.tsystems.client.ScheduleServiceImpl;
import com.tsystems.dto.ScheduleDTO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

// Session scoped makes your property to be available in different pages
@ManagedBean(name = "beanController")
@SessionScoped
public class BeanController {

    @EJB(beanName = ScheduleServiceImpl.JNDI)
    private ScheduleService restClient;

    public String stationName;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<ScheduleDTO> getScheduleForToday() {
        try {
            System.out.println("BeanController getScheduleForToday():");
            System.out.println("Successfully invoked");
            List<ScheduleDTO> resultSchedules = restClient.getScheduleForToday(stationName);
            System.out.println("List<ScheduleDTO>.size(): " + resultSchedules.size());
            return resultSchedules;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String formattedInstant(Instant input) {
        if (input == null) {
            return "-";
        }
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        return formatter.format(input);
    }

}
