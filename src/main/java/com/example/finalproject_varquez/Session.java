package com.example.finalproject_varquez;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public  class Session implements TransactionService {

    private static Session instance;
    private static UserSessionData activeData;
    private static ObservableList<String> fxTransactions = FXCollections.observableArrayList();

    private Session() { // Private constructor for singleton usage
        // Try to recover an unclosed session on application bootup
        UserSessionData recovered = SessionSerializer.loadSession();
        if (recovered != null) {
            activeData = recovered;
            fxTransactions.setAll(recovered.getTransactionHistory());
        }
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public static void startNewSession(String username) {
        // Initial dummy data allocation for testing accounts
        double initialBalance = 12450.75;
        java.util.List<String> initialHistory = java.util.Arrays.asList(
                "Received from Maria S.|+₱500.00",
                "Paid to Cafe Luna|-₱185.00",
                "Cash out to BDO|-₱2,000.00"
        );

        activeData = new UserSessionData(username, initialBalance, initialHistory);
        fxTransactions.setAll(initialHistory);

        // Save file immediately upon successful login execution
        SessionSerializer.saveSession(activeData);
    }

    public static String getCurrentUsername() {
        return activeData != null ? activeData.getUsername() : null;
    }

    public static double getWalletBalance() {
        return activeData != null ? activeData.getBalance() : 0.0;
    }

    public static void deposit(double amount) {
        if (activeData != null) {
            activeData.setBalance(activeData.getBalance() + amount);
            SessionSerializer.saveSession(activeData); // Save file checkpoint updates
        }
    }

    public static boolean withdraw(double amount) {
        if (activeData == null || amount > activeData.getBalance()) {
            return false;
        }
        activeData.setBalance(activeData.getBalance() - amount);
        SessionSerializer.saveSession(activeData); // Save file checkpoint updates
        return true;
    }


    public ObservableList<String> getTransaction() {
        return fxTransactions;
    }

    @Override
    public void logTransaction(String description, String signedAmount) {
        if (activeData != null) {
            String transactionLine = description + "|" + signedAmount;
            fxTransactions.add(0, transactionLine);
            activeData.addTransaction(transactionLine);
            SessionSerializer.saveSession(activeData); // Save change log updates to file
        }
    }

    public static void endSession() {
        activeData = null;
        fxTransactions.clear();
        SessionSerializer.deleteSessionFile(); // Erase evidence on logout
    }
}