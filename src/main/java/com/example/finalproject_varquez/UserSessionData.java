package com.example.finalproject_varquez;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSessionData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private double balance;
    private final List<String> transactionHistory;

    public UserSessionData(String username, double balance, List<String> transactionHistory){
        this.username = username;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>(transactionHistory);
    }

    public String getUsername(){return username;}
    public double getBalance(){return balance;}
    public List<String> getTransactionHistory(){return transactionHistory;}

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(String entry){this. transactionHistory.add(0, entry); }
}
