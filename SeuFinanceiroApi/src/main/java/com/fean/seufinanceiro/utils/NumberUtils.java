package com.fean.seufinanceiro.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtils {

    /**
     * Round double two decimal places
     */
    public static Double roundDouble(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Format double to the pattern 0.00
     */
    public static String formatDouble(double value) {
        DecimalFormat format = new DecimalFormat("0.00");
        String formatted = format.format(value);
        return formatted;
    }

}
