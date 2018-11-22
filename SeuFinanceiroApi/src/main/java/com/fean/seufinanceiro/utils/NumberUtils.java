package com.fean.seufinanceiro.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    /**
     * Format double two decimal places
     */
    public static Double formatDouble(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
