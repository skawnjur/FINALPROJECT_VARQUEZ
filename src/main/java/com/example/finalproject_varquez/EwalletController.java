package com.example.finalproject_varquez;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public class EwalletController {

        @FXML private Label userNameLabel;
        @FXML private Label balanceLabel;
        @FXML private Button notificationButton;
        @FXML private Button cashInButton;
        @FXML private Button cashOutButton;
        @FXML private Button sendButton;
        @FXML private Button payQrButton;
        @FXML private Button billsButton;
        @FXML private Button moreButton;
        @FXML private Button themeToggleButton;
        private boolean isDark = false;
        @FXML private ListView<String> transactionListView;

        private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));


        @FXML
        public void initialize() {
                String username = Session.getCurrentUsername();
                userNameLabel.setText(username != null ? username : "User");

                refreshBalanceLabel();

                transactionListView.setItems(Session.getInstance().getTransaction());
                transactionListView.setCellFactory(list -> new TransactionCell());
        }

        private void refreshBalanceLabel() {
                balanceLabel.setText(currencyFormat.format(Session.getWalletBalance()));
        }

        @FXML
        private void handleCashIn() {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Cash in");
                dialog.setHeaderText(null);
                dialog.setContentText("Amount to cash in:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(input -> {
                        try {
                                double amount = Double.parseDouble(input.trim());
                                if (amount <= 0) {
                                        AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Enter an amount greater than zero.");
                                        return;
                                }
                                Session.deposit(amount);
                                Session.getInstance().logTransaction( "Cash in","+" + currencyFormat.format(amount));
                                refreshBalanceLabel();
                        } catch (NumberFormatException e) {
                                AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Please enter a valid number.");
                        }
                });
        }

        @FXML
        private void handleCashOut() {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Cash out");
                dialog.setHeaderText(null);
                dialog.setContentText("Amount to cash out:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(input -> {
                        try {
                                double amount = Double.parseDouble(input.trim());
                                if (amount <= 0) {
                                        AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Enter an amount greater than zero.");
                                        return;
                                }
                                boolean success = Session.withdraw(amount);
                                if (!success) {
                                        AccountStore.showAlert(AlertType.ERROR, "Insufficient balance", "You don't have enough balance for this cash out.");
                                        return;
                                }
                                Session.getInstance().logTransaction( "Cash out","-" + currencyFormat.format(amount));
                                refreshBalanceLabel();
                        } catch (NumberFormatException e) {
                                AccountStore.showAlert(AlertType.WARNING, "Invalid amount", "Please enter a valid number.");
                        }
                });
        }

        @FXML
        private void handleSendMoney() {
              try{
                      HelloApplication.showSendMoneyScreen();
              } catch (IOException e){
                      e.printStackTrace();
                      AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not open the Send Money screen.");
              }
        }


        @FXML
        private void handlePayMerchant() {
                try {
                        HelloApplication.showPayMerchantScreen();
                } catch (IOException e) {
                        e.printStackTrace();
                        AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not open the Pay Merchant screen.");
                }
        }

        @FXML
        private void handleBills() {
                try{
                        HelloApplication.showBillsScreen();
                } catch (IOException e){
                        e.printStackTrace();
                        AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not open the Bills screen.");
                }
        }

        @FXML
        private void handleMore() {
                // TODO: open a "more options" menu/screen.
                AccountStore.showAlert(AlertType.INFORMATION, "More", "More options coming soon.");
        }

        @FXML
        private void handleNotifications() {
                // TODO: open your Notifications screen once it exists.
                AccountStore.showAlert(AlertType.INFORMATION, "Notifications", "You have no new notifications.");
        }

        @FXML
        public void handleLogout() {
                Alert confirm = new Alert(AlertType.CONFIRMATION);
                confirm.setTitle("Log out");
                confirm.setHeaderText(null);
                confirm.setContentText("Are you sure you want to log out?");

                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                        Session.endSession();
                        try {
                                HelloApplication.showLoginScreen();
                        } catch (IOException e) {
                                e.printStackTrace();
                                AccountStore.showAlert(AlertType.ERROR, "Navigation Error", "Could not load login screen.");
                        }
                }
        }


        @FXML
        private void handleThemeToggle(ActionEvent event) {
                Scene scene = themeToggleButton.getScene();

                if (!isDark) {
                        // 3. Layer dark mode directly on top of your FXML layout
                        scene.getRoot().getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                        themeToggleButton.setText("☀️");
                } else {
                        // 4. Remove ONLY the dark mode stylesheet, revealing Ewallet.css underneath safely
                        scene.getRoot().getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                        themeToggleButton.setText("🌙");
                }

                // 5. Flip the state tracking variable
                isDark = !isDark;
        }



        /**
         * Renders each transaction as "Description" on the left and
         * a color-coded amount on the right, similar to the UI mockup.
         * Each list item is stored as "description|amount" for simplicity —
         * swap this for a real Transaction object once you have one.
         */
        private static class TransactionCell extends ListCell<String> {
                @Override
                protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                                setText(null);
                                setGraphic(null);
                                return;
                        }

                        String[] parts = item.split("\\|");
                        String description = parts[0];
                        String amount = parts.length > 1 ? parts[1] : "";

                        Label descLabel = new Label(description);
                        Label amountLabel = new Label(amount);

                        // Apply custom class for positive amounts
                        if (amount.startsWith("+")) {
                                if (!amountLabel.getStyleClass().contains("amount-green")) {
                                        amountLabel.getStyleClass().add("amount-green");
                                }
                                amountLabel.setStyle("-fx-font-weight: bold;");
                        } else {
                                amountLabel.getStyleClass().remove("amount-green");
                                amountLabel.setStyle(null); // Reset inline style completely
                        }

                        Region spacer = new Region();
                        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

                        HBox row = new HBox(descLabel, spacer, amountLabel);
                        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                        setText(null);
                        setGraphic(row);
                }
        }
    }

