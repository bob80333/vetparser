package com.erice.vetparser;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static Calendar convertDate(int[] rawDate) {
        int numDays = rawDate[1] + (rawDate[0] << 8);
        System.out.println(numDays);
        Calendar calendar = new Calendar.Builder().setCalendarType("gregorian").setDate(1958, 11, 12).build();
        calendar.add(Calendar.DATE, numDays);
        System.out.println(calendar.toInstant());

        return calendar;
    }

    public static int calculateDays(int[] days) {
        return days[1] + (days[0] << 8);
    }

    public static int[] convertCharToInt(char[] data) {
        int[] intData = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            intData[i] = data[i] & 0xFFFF;
        }

        return intData;
    }


    public static int[] convertByteToInt(byte[] data) {
        int[] intData = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            intData[i] = data[i] & 0xFF;
        }

        return intData;
    }

    // modified version of https://stackoverflow.com/a/9855338

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return "0x" + new String(hexChars);
    }
}
