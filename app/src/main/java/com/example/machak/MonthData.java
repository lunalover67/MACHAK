package com.example.machak;

import java.util.ArrayList;

public class MonthData {

    private ArrayList<Transaction> transactionLog;
    private double spent, budget;
    private int month_index, year;


    public MonthData() {
        budget = 500;

        transactionLog = new ArrayList<>();
    }

    public MonthData(double budget_input, int month_index_input, int year_input) {
        budget = budget_input;
        month_index = month_index_input;
        year = year_input;
        transactionLog = new ArrayList<>();
    }

//    public getTimeStamp


}
