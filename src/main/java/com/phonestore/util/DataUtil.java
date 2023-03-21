package com.phonestore.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class DataUtil {

    private static final Locale vn = new Locale("vi", "vn");
    private static final NumberFormat vnNumber = NumberFormat.getCurrencyInstance(vn);

    private DataUtil() {}

    public static int safeToInt(Object object) {
        if (!isNumber(object)) {
            return 0;
        }
        return Integer.valueOf(object.toString());
    }

    public static double safeToDouble(Object object) {
        if (!isNumber(object)) {
            return 0.0;
        }
        return Double.valueOf(object.toString());
    }

    public static String safeToString(Object object) {
        if (Objects.isNull(object)) {
            return "";
        }
        return object.toString();
    }

    public static boolean isNumber(Object object) {
        try {
            Double.valueOf(object.toString());
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    public static String repeat(int count, String value) {
        if (count < 0) {
            throw new IllegalArgumentException("count is negative: " + count);
        }
        if (count == 1) {
            return value;
        }
        final int len = value.length();
        if (len == 0 || count == 0) {
            return "";
        }

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < count; i++) {
            str.append(value);
        }
        return str.toString();
    }

    public static String formatCurrency(Number number) {
        return vnNumber.format(number);
    }
}