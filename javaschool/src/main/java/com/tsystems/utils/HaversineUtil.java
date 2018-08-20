package com.tsystems.utils;

import com.tsystems.dto.StationDTO;

/**
 * Performs distance calculations between stations
 */
public class HaversineUtil {
    private static final int PI_VALUE_IN_DEGREES = 180;
    private final static int EARTH_RADIUS = 6371;

    private HaversineUtil() {
    }

    /**
     * Calculcates distance between two stations
     *
     * @param startPoint startPoint
     * @param endPoint endPoint
     * @return distance
     */
    public static double calculateDistanceInKm(StationDTO startPoint, StationDTO endPoint) {
        Double latitureStartPoint = startPoint.getLatitude();
        Double longitudeStartPoint = startPoint.getLongitude();
        Double latitureEndPoint = endPoint.getLatitude();
        Double longitudeEndPoint = endPoint.getLongitude();
        Double latDistance = toRad(latitureEndPoint - latitureStartPoint);
        Double lonDistance = toRad(longitudeEndPoint - longitudeStartPoint);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(latitureStartPoint)) * Math.cos(toRad(latitureEndPoint)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    /**
     * Returns radians from degrees
     *
     * @param value degrees
     * @return radians
     */
    private static Double toRad(Double value) {
        return value * Math.PI / PI_VALUE_IN_DEGREES;
    }

}
