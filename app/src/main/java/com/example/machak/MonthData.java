package com.example.machak;

import java.util.ArrayList;

public class MonthData {

    private ArrayList<Transaction> transactionLog;
    private double spent, budget;
    private int month, year;


    // creates new month data obj
    public MonthData() {
        transactionLog = new ArrayList<>();
        month = Timestamp.getCurrentMonth();
        year = Timestamp.getCurrentYear();
        budget = 500;
        spent = 0;
    }

    public MonthData(double budget_input, int month_index_input, int year_input) {
        budget = budget_input;
        month = month_index_input;
        year = year_input;
        transactionLog = new ArrayList<>();
    }

//    public getTimeStamp


    public ArrayList<Transaction> getTransactionLog() {
        return transactionLog;
    }

    public double getSpent() {
        return spent;
    }

    public double getBudget() {
        return budget;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
