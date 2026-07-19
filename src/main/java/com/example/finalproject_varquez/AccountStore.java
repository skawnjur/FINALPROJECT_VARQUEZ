package com.example.finalproject_varquez;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.HashMap;
import java.util.Map;


    public class AccountStore{

        public static final Map<String, String> DUMMY_ACCOUNTS = new HashMap<>();

        static {
            DUMMY_ACCOUNTS.put("09171234567", "password123");
            DUMMY_ACCOUNTS.put("juan@example.com", "password123");
        }

        public static void showAlert(AlertType type, String title, String message) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

