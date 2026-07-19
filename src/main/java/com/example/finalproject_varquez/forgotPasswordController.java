package com.example.finalproject_varquez;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class forgotPasswordController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleResetPassword() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String newPassword = newPasswordField.getText() == null ? "" : newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText();

        if (username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            AccountStore.showAlert(AlertType.WARNING, "Missing information", "Please fill in all fields.");
            return;
        }

        if (!AccountStore.DUMMY_ACCOUNTS.containsKey(username)) {
            AccountStore.showAlert(AlertType.ERROR, "Account not found", "No account found with that mobile number/email.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            AccountStore.showAlert(AlertType.WARNING, "Password mismatch", "Passwords do not match.");
            return;
        }

        if (newPassword.length() < 8) {
            AccountStore.showAlert(AlertType.WARNING, "Weak password", "Password must be at least 8 characters long.");
            return;
        }

        AccountStore.DUMMY_ACCOUNTS.put(username, newPassword);

        AccountStore.showAlert(AlertType.INFORMATION, "Password reset", "Your password has been updated. Please log in.");

        try {
            HelloApplication.showLoginScreen();
        } catch (Exception e) {
            e.printStackTrace();
            AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not return to the login screen.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            HelloApplication.showLoginScreen();
        } catch (Exception e) {
            e.printStackTrace();
            AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not return to the login screen.");
        }
    }
}
