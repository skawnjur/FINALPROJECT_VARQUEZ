package com.example.finalproject_varquez;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


public class loginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AccountStore.showAlert(AlertType.WARNING, "Missing information", "Please enter both your mobile number/email and password.");
            return;
        }

        String storedPassword = AccountStore.DUMMY_ACCOUNTS.get(username);
        if (storedPassword == null || !storedPassword.equals(password)) {
            AccountStore.showAlert(AlertType.ERROR, "Login failed", "Incorrect mobile number/email or password.");
            return;
        }

        Session.startNewSession(username);

        try {
            HelloApplication.showHomeScreen();
        } catch (Exception e) {
            e.printStackTrace();
           AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not open the home screen.");
        }
    }
    @FXML
    private void onPasswordKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            handleLogin();
        }
    }


    @FXML
    private void handleRegister() {
        try{
            HelloApplication.showRegisterScreen();
        } catch (Exception e){
            e.printStackTrace();

        }

    }

    @FXML
    private void handleForgotPassword(){
            try{
                HelloApplication.showForgotPasswordScreen();
            }catch(Exception e){
                e.printStackTrace();
                AccountStore.showAlert(AlertType.ERROR, "Something went wrong", "Could not open the reset passwowrd scree.");
            }
    }




}