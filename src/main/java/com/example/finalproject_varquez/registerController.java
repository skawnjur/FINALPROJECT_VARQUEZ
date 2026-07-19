package com.example.finalproject_varquez;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class registerController {
    @FXML
    private TextField fullnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleRegister() {
        String fullname = fullnameField.getText() == null ? "" : fullnameField.getText().trim();
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText().trim();

        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            AccountStore.showAlert(Alert.AlertType.WARNING, "Missing information", "Please fill in fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            AccountStore.showAlert(Alert.AlertType.WARNING, "Password mismatch", "Password do not match.");
            return;
        }

        if (password.length() < 8) {
            AccountStore.showAlert(Alert.AlertType.WARNING, "Weak password", "Password must be at least 8 characters long.");
            return;
        }
        if (AccountStore.DUMMY_ACCOUNTS.containsKey((username))) {
            AccountStore.showAlert(Alert.AlertType.ERROR, "Registration failed", "An account with that mobile number/email already exist.");
            return;
        }
        AccountStore.DUMMY_ACCOUNTS.put(username, password);

        AccountStore.showAlert(Alert.AlertType.INFORMATION, "Registration successful", "Your account has been created. Please log in.");

    }
    @FXML
        private void handleBackToLogin(){
            try{
                HelloApplication.showLoginScreen();
            } catch (Exception e){
                e.printStackTrace();

            }
        }
    }
