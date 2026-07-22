package com.example.finalproject_varquez;

import javafx.collections.ObservableList;

public interface TransactionService {
    ObservableList<String> getTransaction();
    void logTransaction(String description, String signedAmount);
}
