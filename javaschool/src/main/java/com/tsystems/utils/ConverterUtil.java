package com.tsystems.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Util class for processing some converter operations
 *
 */
public class ConverterUtil {
    private static final Logger log = Logger.getLogger(ConverterUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    private ConverterUtil() {
    }

    /**
     * Converts string to Instant
     *
     * @param timestamp timestamp
     * @return parsed Instant
     */
    public static Instant parseInstant(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        TemporalAccessor temporalAccessor = formatter.parse(timestamp);
        LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return Instant.from(zonedDateTime);
    }

    /**
     * Converts instant to formatted string
     *
     * @param input - instant which needs to be parsed
     * @return formatted string
     */
    public static String getFormattedString(Instant input) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(Locale.UK)
                        .withZone(ZoneId.systemDefault());
        return formatter.format(input);
    }

    /**
     * Parse list of milliseconds to list<long>
     *
     * @param hoursAndMinutes hoursAndMinutes
     * @return list long of milliseconds
     */
    public static List<Long> parseMilliseconds(List<String> hoursAndMinutes) {
        List<Long> resultMillisecondsList = new ArrayList<>();
        for (String timeElement : hoursAndMinutes) {
            List<String> timeParts = new ArrayList<>(Arrays.asList(timeElement.split(":")));
            Long minutes = (long) ((Integer.parseInt(timeParts.get(0)) * 60) + Integer.parseInt(timeParts.get(1)));
            Long seconds = minutes * 60;
            Long milliseconds = seconds * 1000;
            resultMillisecondsList.add(milliseconds);
        }
        return resultMillisecondsList;
    }

    /**
     * Converts list to json
     *
     * @param inputList inputList
     * @return string json
     */
    public static String parseJson(List<?> inputList) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            objectMapper.writeValue(out, inputList);
            return new String(out.toByteArray());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
