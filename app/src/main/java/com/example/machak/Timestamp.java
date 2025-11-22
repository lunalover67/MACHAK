package com.example.machak;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Timestamp {

    // issue is calendar.get(Calendar.INDEX) <-- used methods are deprecated
    private int minute, hour, day, month, year;

    public static final Map<Integer, String> MONTH_DICTIONARY = Map.ofEntries(
            Map.entry(0, "JANUARY"),
            Map.entry(1, "FEBRUARY"),
            Map.entry(2, "MARCH"),
            Map.entry(3, "APRIL"),
            Map.entry(4, "MAY"),
            Map.entry(5, "JUNE"),
            Map.entry(6, "JULY"),
            Map.entry(7, "AUGUST"),
            Map.entry(8, "SEPTEMBER"),
            Map.entry(9, "OCTOBER"),
            Map.entry(10, "NOVEMBER"),
            Map.entry(11, "DECEMBER")
    );


    public Timestamp() {

        // Get device time.
        Date currentTime = Calendar.getInstance().getTime();

        // Set class variables.
        minute = currentTime.getMinutes();
        hour = currentTime.getHours();
        day = currentTime.getDate();
        month = currentTime.getMonth();
        year = 1900 + currentTime.getYear();

    }

    public String toString() {

        String formatted_minute;

        if (minute < 10) {
            formatted_minute = "0" + minute;
        }
        else {
            formatted_minute = Integer.toString(minute);
        }


        return String.format("%d %s %d @ %d:%s", day, MONTH_DICTIONARY.get(month), year, hour, formatted_minute);
    }

    // returns 0-11, not 1-12
    public static int getCurrentMonth() {
        return Calendar.getInstance().getTime().getMonth();
    }

    public static int getCurrentYear() {
        return (1900 + Calendar.getInstance().getTime().getYear());
    }

}

