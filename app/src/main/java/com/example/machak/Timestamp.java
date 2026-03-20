package com.example.machak;

import android.annotation.SuppressLint;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Timestamp {


    // _____________________ CROSS_CLASS_VARS _____________________


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


    // ______________________ INSTANCE_VARS ______________________


    // [NOTE] issue is calendar.get(Calendar.INDEX) <-- used methods are deprecated
    private int minute, hour, day, month, year;


    // _______________________ CONSTRUCTOR _______________________


    public Timestamp() {

        // Get device time.
        Date currentTime = Calendar.getInstance().getTime();

        // Set class variables.
        // [LATER] Replace depreciated methods.
        minute = currentTime.getMinutes();
        hour = currentTime.getHours();
        day = currentTime.getDate();
        month = currentTime.getMonth();
        year = 1900 + currentTime.getYear(); // weird O.O

    }

    // _________________________ GETTERS _________________________


    @SuppressLint("DefaultLocale")
    public String getFormattedDate() {
        // Format with leading zeros where needed
        return String.format("%04d-%02d-%02d %02d:%02d",
                year, month + 1, day, hour, minute);
    }


    // [NOTE] Returns 0-11, not 1-12.
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH); // 0-11, reliable
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public int getDay() {
        return day;
    }

    // [NOTE] Returns 0-11, not 1-12.
    public int getMonth() {
        return month;
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

}

