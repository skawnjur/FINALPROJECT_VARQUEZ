package com.example.finalproject_varquez;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;


public class loginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink forgotPasslink;
    @FXML
    private Button loginButton;
    @FXML
    private Button fingerprintButton;
    @FXML
    private Hyperlink signUpLink;

    private static final Map<String, String> DUMMY_ACCOUNTS = new HashMap<>();

    static {
        DUMMY_ACCOUNTS.put("09171234567", "password123");
        DUMMY_ACCOUNTS.put("juan@example.com", "password123");
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.WARNING, "Missing information", "Please enter both your mobile number/email and password.");
            return;
        }

        String storedPassword = DUMMY_ACCOUNTS.get(username);
        if (storedPassword == null || !storedPassword.equals(password)) {
            showAlert(AlertType.ERROR, "Login failed", "Incorrect mobile number/email or password.");
            return;
        }

        Session.setCurrentUsername(username);

        try {
            HelloApplication.showHomeScreen();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Something went wrong", "Could not open the home screen.");
        }
    }

    @FXML
    private void handleFingerprintLogin() {
        // TODO: hook up real biometric authentication.
        showAlert(AlertType.INFORMATION, "Fingerprint login", "Fingerprint login is not set up yet.");
    }

    @FXML
    private void handleSignUp() {
        // TODO: navigate to your registration screen once it exists.
        showAlert(AlertType.INFORMATION, "Sign up", "Sign-up screen coming soon.");
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}