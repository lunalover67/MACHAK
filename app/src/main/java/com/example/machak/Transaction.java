package com.example.machak;

import android.annotation.SuppressLint;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Transaction {

    // _____________________ CROSS_CLASS_VARS _____________________


    public static final HashMap<String, String> TAGS = new HashMap<>();

    static {
        TAGS.put("GROC", "Groceries");
        TAGS.put("EATS", "Eating Out");
        TAGS.put("MISC", "Miscellaneous");
    }

    // ________________________ CLASS_VARS ________________________

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");


    // ______________________ INSTANCE_VARS ______________________


    private String location;
    private double amount;
    private Timestamp timestamp;
    private String tag;


    // _______________________ CONSTRUCTOR _______________________



    public Transaction(String location_input, double amount_input, Timestamp timestamp_input, String tag_input) {
        location = location_input;
        amount = amount_input;
        timestamp = timestamp_input;
        tag = tag_input;
    }


    // _________________________ GETTERS _________________________


    public String getLocation() {
        return location;
    }

    public double getAmount() {
        return amount;
    }

    public String getTag() {
        return tag;
    }

    @SuppressLint("DefaultLocale")
    public String getSummary() {

            /*
            "[2025-11-24 14:30] $12.50 @ New York"
            "[NYC] [$12.50] [2025-11-24 14:30]"
            "NYC / $12.50 / 14:30"
            "NYC: $12.50 (2025-11-24 14:30)"
             */

        return String.format("$%s @ %s [%s]", DECIMAL_FORMAT.format(amount), location, timestamp.getFormattedDate()) + tag;
    }

}
