package com.tsystems.utils;

/**
 * Performs rounding operations
 */
public class RoundUtil {

    private RoundUtil() {
    }

    /**
     * Returns rounded double by places
     *
     * @param value value to round
     * @param places places
     * @return rounded double
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
