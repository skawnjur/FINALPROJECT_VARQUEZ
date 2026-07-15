package com.example.finalproject_varquez;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showLoginScreen();
        primaryStage.setTitle("E-Wallet");
        primaryStage.show();
    }

    public static void showLoginScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        addStylesheetIfPresent(scene, "login.css");
        primaryStage.setScene(scene);
    }

    public static void showHomeScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        addStylesheetIfPresent(scene, "home.css");
        primaryStage.setScene(scene);
    }

    // Adds the stylesheet only if it exists yet, so the app doesn't crash
    // while you haven't created the CSS files. Add the CSS whenever you're ready.
    private static void addStylesheetIfPresent(Scene scene, String cssFileName) {
        java.net.URL cssUrl = HelloApplication.class.getResource(cssFileName);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}