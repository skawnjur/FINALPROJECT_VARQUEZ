package com.example.finalproject_varquez;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
public class billsController {

    @FXML private Label balanceLabel;
    @FXML private ComboBox<String> billerComboBox;
    @FXML private TextField accountNumberField;
    @FXML private TextField amountField;


    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

    // TODO: replace with real billers from your data layer.
    private static final String[] SAMPLE_BILLERS = {
            "Meralco (Electricity)",
            "Manila Water",
            "PLDT Home",
            "Globe Postpaid",
            "Converge ICT"
    };

    @FXML
    public void initialize() {
        balanceLabel.setText(currencyFormat.format(Session.getWalletBalance()));
        billerComboBox.setItems(FXCollections.observableArrayList(SAMPLE_BILLERS));
    }

    @FXML
    private void handlePayBill() {
        String biller = billerComboBox.getValue();
        String accountNumber = accountNumberField.getText() == null ? "" : accountNumberField.getText().trim();
        String amountText = amountField.getText() == null ? "" : amountField.getText().trim();

        if (biller == null || biller.isEmpty()) {
            AccountStore.showAlert(AlertType.WARNING, "No biller selected", "Please select a biller to pay.");
            return;
        }

        if (accountNumber.isEmpty()) {
            AccountStore.showAlert(AlertType.WARNING, "Missing account number", "Please enter your account or reference number.");
            return;
        }

        if (amountText.isEmpty()) {
            AccountStore.showAlert(AlertType.WARNING, "Missing amount", "Please enter an amount to pay.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Please enter a valid number.");
            return;
        }

        if (amount <= 0) {
            AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Enter an amount greater than zero.");
            return;
        }

        boolean success = Session.withdraw(amount);
        if (!success) {
            AccountStore.showAlert(AlertType.ERROR, "Insufficient balance", "You don't have enough balance to pay this bill.");
            return;
        }

        Session.getInstance().logTransaction("Bill payment - " + biller, "-" + currencyFormat.format(amount));

        AccountStore.showAlert(AlertType.INFORMATION, "Bill paid", "You paid " + currencyFormat.format(amount) + " to " + biller + ".");

        try {
            HelloApplication.showHomeScreen();
        } catch (IOException e) {
            e.printStackTrace();
            AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not return to the home screen.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            HelloApplication.showHomeScreen();
        } catch (IOException e) {
            e.printStackTrace();
            AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not return to the home screen.");
        }
    }
}
