package com.example.finalproject_varquez;

//temporary act as a database
public class Session {
    private static String currentUsername;
    private static double walletBalance = 12450.75;

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    public static double getWalletBalance() {
        return walletBalance;
    }

    public static void deposit(double amount) {
        walletBalance += amount;
    }

    public static boolean withdraw(double amount) {
        if (amount > walletBalance) {
            return false;
        }
        walletBalance -= amount;
        return true;
    }
}

