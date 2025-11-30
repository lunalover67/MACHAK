package com.example.machak;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Transaction {

    // ----

    public static final HashMap<String, String> TAGS = new HashMap<>();

    static {
        TAGS.put("BUSF", "Bus Fares");
        TAGS.put("GROC", "Groceries");
        TAGS.put("EATS", "Eating Out");
        TAGS.put("MISC", "Miscellaneous");
    }

    // ----

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");



    private String location;
    private double amount;
    private Timestamp timestamp;
    private String tag;



    // -- CONSTRUCTOR



    public Transaction(String location_input, double amount_input, Timestamp timestamp_input, String tag_input) {
        location = location_input;
        amount = amount_input;
        timestamp = timestamp_input;
        tag = tag_input;
    }



    // -- GETTER METHODS



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


//        return "hiya";
        return String.format("$%s @ %s [%s]", DECIMAL_FORMAT.format(amount), location, timestamp.getFormattedDate()) + tag;
    }

    // -- FUNCTION METHODS


    public static void appendTransactionToLog(String location_input, double amount_input, Timestamp timestamp_input, String tag_input) {

        // read file, get list of transactions

        Transaction new_transaction = new Transaction(location_input, amount_input, timestamp_input, tag_input);

        // transaction list.append(transaction)

        // update file, close reader/writer
    }



}
