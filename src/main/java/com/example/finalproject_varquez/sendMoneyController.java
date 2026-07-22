package com.example.finalproject_varquez;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class sendMoneyController {
    @FXML private Button backButton;
    @FXML private Label balanceLabel;
    @FXML private TextField recipientField;
    @FXML private TextField amountField;
    @FXML private  TextField messageField;
    @FXML private  Button sendButton;

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

    @FXML public void initalize(){
        balanceLabel.setText(currencyFormat.format(Session.getWalletBalance()));

    }
    @FXML private void handleSend(){
        String recipient = recipientField.getText() == null ? "" : recipientField.getText().trim();
        String amountText = amountField.getText() == null ? "" : amountField.getText().trim();

        if(recipient.isEmpty()){
            AccountStore.showAlert(AlertType.WARNING, "Missing recipient", "Please enter a recipient mobile number or email.");
            return;
        }

        if(amountText.isEmpty()){
            AccountStore.showAlert(AlertType.WARNING, "Missing amount", "Please enter an amount to send.");
            return;
        }
        double amount;
        try{
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e){
            AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Please enter a valid number.");
            return;
        }
        if(amount <= 0){
            AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Please enter amont greater than zero");
            return;
        }

        boolean success = Session.withdraw(amount);
        if(!success){
            AccountStore.showAlert(AlertType.ERROR, "Insufficient balance", "You don't have enough balance to send this amount.");
            return;
        }
        Session.getInstance().logTransaction("Sent to " + recipient, "-" + currencyFormat.format(amount));
        AccountStore.showAlert(AlertType.INFORMATION, "Money sent", "You sent"
                + currencyFormat.format(amount) + " to " + recipient + ".");
        try{
            HelloApplication.showHomeScreen();
        } catch (IOException e){
            e.printStackTrace();
            AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not return to the home screen.");
        }
    }

    @FXML private  void handleBack(){
        try{
            HelloApplication.showHomeScreen();
        } catch (IOException e) {
            e.printStackTrace();
            AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not return to the home screen.");
        }
    }
}
