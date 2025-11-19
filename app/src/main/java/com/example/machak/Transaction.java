package com.example.machak;

public class Transaction {



    private String location;
    private double amount;
    private Timestamp timestamp;



    // -- CONSTRUCTOR



    public Transaction(String location_input, double amount_input, Timestamp timestamp_input) {
        location = location_input;
        amount = amount_input;
        timestamp = timestamp_input;
    }



    // -- GETTER METHODS



    public String getLocation() {
        return location;
    }

    public double getAmount() {
        return amount;
    }



    // -- FUNCTION METHODS



    public static void appendTransactionToLog(String location_input, double amount_input, Timestamp timestamp_input) {

        // read file, get list of transactions

        Transaction new_transaction = new Transaction(location_input, amount_input, timestamp_input);

        // transaction list.append(transaction)

        // update file, close reader/writer
    }



}
