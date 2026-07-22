package com.example.finalproject_varquez;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class paymerchantController {

    @FXML private Button backButton;
    @FXML private Label balanceLabel;
    @FXML private ComboBox<String> merchantComboBox;
    @FXML private TextField amountField;
    @FXML private TextField noteField;
    @FXML private Button payButton;

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

    // TODO: replace with real merchant data (e.g. from a QR code payload or a database).
    private static final String[] SAMPLE_MERCHANTS = {
            "Cafe Luna",
            "Jollibee - SM City",
            "7-Eleven - Rizal St.",
            "Watsons Pharmacy"
    };

    @FXML
    public void initialize() {
        balanceLabel.setText(currencyFormat.format(Session.getWalletBalance()));
        merchantComboBox.setItems(FXCollections.observableArrayList(SAMPLE_MERCHANTS));
    }

    @FXML
    private void handlePay() {
        String merchant = merchantComboBox.getValue();
        String amountText = amountField.getText() == null ? "" : amountField.getText().trim();

        if (merchant == null || merchant.isEmpty()) {
            AccountStore.showAlert(AlertType.WARNING, "No merchant selected", "Please select a merchant to pay.");
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
            AccountStore.showAlert(AlertType.ERROR, "Insufficient balance", "You don't have enough balance to pay this amount.");
            return;
        }

        Session.getInstance().logTransaction("Paid to " + merchant, "-" + currencyFormat.format(amount));

        AccountStore.showAlert(AlertType.INFORMATION, "Payment successful", "You paid " + currencyFormat.format(amount) + " to " + merchant + ".");

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
