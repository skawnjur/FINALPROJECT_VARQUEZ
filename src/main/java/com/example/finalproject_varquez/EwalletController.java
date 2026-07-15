package com.example.finalproject_varquez;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EwalletController {


        @FXML private Label userNameLabel;
        @FXML private Label balanceLabel;
        @FXML private Button notificationButton, cashInButton, cashOutButton;
        @FXML private Button sendButton, payQrButton, billsButton, moreButton;
        @FXML private ListView<String> transactionListView;

        @FXML
        private void handleNotifications() { }

        @FXML
        private void handleCashIn() { }

        @FXML
        private void handleCashOut() { }

        @FXML
        private void handleSendMoney() { }

        @FXML
        private void handlePayMerchant() { }

        @FXML
        private void handleBills() { }

        @FXML
        private void handleMore() { }
    }

