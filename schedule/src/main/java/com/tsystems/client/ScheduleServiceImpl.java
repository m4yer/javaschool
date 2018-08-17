package com.tsystems.client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tsystems.dto.ScheduleDTO;
import com.tsystems.dto.TrainDTO;
import com.tsystems.dto.TripDTO;
import com.tsystems.util.ScheduleDeserializer;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.List;

@Stateless(name = ScheduleServiceImpl.JNDI)
public class ScheduleServiceImpl implements ScheduleService {

    public static final String JNDI = "restClientBean";
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method does HTTP GET request and return JSON response
     *
     * @param url - url of request
     * @return String - json response
     * @throws IOException when response code != 200. i.e when not ok
     */
    private static String httpGet(String url) throws IOException {
        java.net.URL urlRequest = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlRequest.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        if (connection.getResponseCode() != 200) {
            System.out.println("CODE IS NOT 200 EXCEPTION");
            throw new IOException("Some HTTP code error. Code is not 200.");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String jsonResponse = bufferedReader.readLine();
        connection.disconnect();
        return jsonResponse;
    }

    public List<String> getAllStations() throws IOException {
        String jsonResponse = httpGet("http://localhost:8080/station/get/list/title");
        try {
            JavaType stringListClass = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);
            return objectMapper.readValue(jsonResponse, stringListClass);
        } catch (IOException e) {
            // TODO: What to return if exception occurs ?
            return null;
        }
    }

    public List<ScheduleDTO> getScheduleForToday(String stationName) throws IOException {
        String jsonResponse = httpGet("http://localhost:8080/schedule/get/?stationName=" + stationName);
        System.out.println("response getScheduleForToday(): " + jsonResponse);
        System.out.println("BREAKPOINT AFTER");
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ScheduleDTO.class, new ScheduleDeserializer());
        objectMapper.registerModule(module);
        try {
            JavaType scheduleListClass = objectMapper.getTypeFactory().constructCollectionType(List.class, ScheduleDTO.class);
            List<ScheduleDTO> resultList = objectMapper.readValue(jsonResponse, scheduleListClass);
            System.out.println("SUCCESSFULLY DESERIALIZED");
            return resultList;
        } catch (IOException e) {
            System.out.println("DESERIALIZING EXCEPTION");
            // TODO: What to return if exception occurs ?
            System.out.println(e.getMessage());
            return null;
        }
    }
}
