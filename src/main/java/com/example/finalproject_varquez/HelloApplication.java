package com.example.finalproject_varquez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage stage;


    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Session.getInstance();

         // Check if a saved session was successfully recovered from the serialized file
        if (Session.getCurrentUsername() != null) {
            // Persistent session found: skip login and route straight to home screen
            showHomeScreen();
        } else {
            // No session file found: proceed to the standard login sequence
            showLoginScreen();
        }

        stage.show();
    }

    public static void showLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("E-Wallet");
        stage.setScene(scene);
    }

      public static void showRegisterScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("E-Wallet - register");
        stage.setScene(scene);
    }

    public static void showForgotPasswordScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgotpass-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("E-Wallet - reset password");
        stage.setScene(scene);
    }

    public static void showHomeScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Ewallet-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("E-Wallet - Home");
        stage.setScene(scene);
    }
    public static void showSendMoneyScreen() throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sendmoney-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
    public static void showPayMerchantScreen() throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("paymerchant-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

}
