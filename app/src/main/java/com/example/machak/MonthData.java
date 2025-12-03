package com.example.machak;

import java.util.ArrayList;

public class MonthData {


    // ______________________ INSTANCE_VARS ______________________


    private ArrayList<Transaction> transactionLog;
    private double spent, budget;
    private int month, year;


    // _______________________ CONSTRUCTORS _______________________


    public MonthData() {
        transactionLog = new ArrayList<>();
        month = Timestamp.getCurrentMonth();
        year = Timestamp.getCurrentYear();
        budget = 500;
        spent = 0;
    }

    // [LATER] When budgets are implemented, this will be used.
    public MonthData(double budget_input, int month_index_input, int year_input) {
        budget = budget_input;
        month = month_index_input;
        year = year_input;
        spent = 0;
        transactionLog = new ArrayList<>();
    }


    // _________________________ GETTERS _________________________


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


    // ________________________ FUNCTIONS ________________________


    // ---------- APPEND_TRANSACTION ----------


    public void appendTransaction(Transaction transaction) {
        transactionLog.add(transaction);
        spent += transaction.getAmount();
    }

}
